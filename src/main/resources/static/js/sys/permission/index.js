var per=new Vue({
	el:'#app',
	data:{
		button_size:'small',
		roles:[],
		stripe:true,
		bg:'#F8F8F9',//浅灰色
		param:{
			status:1
		},
		select_role:{},
		loading:true,
		containerhei:0,
		menus:[
			{
				title: "菜单管理",
				expand: true,//默认展开
				icon: "ios-apps",
				id: 0,
				menuname:"菜单管理",
				pid: '',
				// checked:false,
				children:[]
			}
		],//树形数组
		menu_datas:[],//原数组
		role_columns:[
			{
				type: 'index',
				width: 60,
				align: 'center'
			},
			{
				title: '角色编码',
				key: 'rolename',
				width:150,
				align: 'center',
			},
			{
				title: '角色名称',
				key: 'role',
				width:150,
				align: 'center'
			},
			{
				title: '备注',
				key: 'memo',
				width:150,
				align: 'center'
			},
			{
				title: '操作',
				key: 'action',
				// fixed: 'right',
				width: 180,
				render: (h, params) => {
					return h('div', [
						h('Button', {
							props: Object.assign({}, this.buttonProps, {
								icon: 'ios-lock-outline',//ios-unlock-outline
								type: 'primary',
								size: 'small',
								// shape:"circle"
								//ghost:'ghost',
							}),
							style: {
								marginRight: '8px'
							},
							on: {
								click: () => {
									per.getPermissions(params);
								}
							}
						}, '权限'),
					]);
				}
			}
		],
	},
	methods:{
		getPermissions:function(params){
			per.select_role=params.row;
			//选中的标签包含父标签, 直接设置就行
			var selected_menus=params.row.menus.split(',');
			per.menu_datas.forEach(function (item2, index2, data2) {
				per.menu_datas[index2].checked=false;
				selected_menus.forEach(function (item,index,data){
					if(item==item2.id){
						per.menu_datas[index2].checked=true;
					}
				});
			});
			//深度复制一份, 是为了防止其他引用导致的ivew, checked与selected不一致的问题
			var newMenu_datas=common.deepCopy(per.menu_datas);
			per.init(newMenu_datas);
		},
		checkChange:function(data, item){
			console.log(data);
			console.log(item);
		},
		saveRoles:function(){
			var nodes=per.$refs.menu_datas.getCheckedNodes();
			var menus=[];
			nodes.forEach(function (item,index) {
				menus.push(item.id);
			})
			per.select_role.menus=menus.join(',');//这里是是选中的菜单ids
			axios({
				url: ctxPath + 'sys/role/addOrUpdate',
				method: "post",
				params:per.select_role})
				.then( res=>{return res.data})
				.then( data => {
					per.getRoles();
					this.$Message.success(data.msg);
				});
		},
		getRoles:function () {
			axios({
				url: ctxPath + 'sys/role/getAll',
				method: "post",
				params:per.param})
				.then(function (response) {
					per.roles=response.data.data;
				})
				.catch(function (error) {
					per.$Message.error(error.response.data.message);
				});
		},
		getMenus:function () {
			axios({
				url: ctxPath + 'sys/menu/getAll',
				method: "post",
				params:{status:1}})
				.then(function (response) {
					per.init(response.data.data);
				})
				.catch(function (error) {
					per.$Message.error(error.response.data.message);
				});;
		},
		init:function (data) {
			this.menu_datas=data;
			var memu_list=common.filterArrayToTree(data,0);
			this.menus[0].children=memu_list;
		},
	}
});


$(function () {
	per.getRoles();
	per.getMenus();
	per.loading=false;
	per.containerhei=$(".container-fluid").height()-35;
});