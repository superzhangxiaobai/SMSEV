var menu=new Vue({
	el: '#app',
	data:{
		selected_node:'',
		menus:[],
		loading:true,       // 首页是否加载
		containerhei:0,
		menu_datas:[],
		menu_form:{},
		menu_rules:{
			title:[
				{ required: true, message: '该项为必填项', trigger: 'blur' },
				{ type: 'string', min: 10, message: '改项字段长度最大为10', trigger: 'blur' }
			]
		},
		menu_types:[{type:'M',lable:'菜单'},{type:'F',lable:'功能'}],
		param:{status:1,},
		is_show:false,
		is_show_icon:false,
		icons:['ios-image-outline','ios-radio-button-off','ios-add','ios-alarm-outline','ios-analytics-outline','ios-apps-outline','ios-at-outline','ios-basket-outline','ios-boat-outline','ios-book-outline','ios-body-outline','ios-bowtie-outline','ios-build-outline','ios-calculator-outline','ios-bus-outline','ios-chatboxes-outline','ios-chatbubbles-outline','ios-close-circle-outline','ios-cloud-outline','ios-cloud-done-outline','ios-cloud-download-outline','ios-cloud-upload-outline','ios-cloudy-night-outline','ios-contacts-outline','ios-desktop-outline','ios-image-outline','ios-key-outline','ios-lock-outline','ios-nuclear-outline','ios-paper-outline']
	},
	methods:{
		renderContent (h, { root, node, data }) {
			return h('span', {
				style: {
					display: 'inline-block',
					width: '100%'
				}
			}, [
				h('span',[
					h('Icon', {
						props: {
							type: 'ios-paper-outline'
						},
						style: {
							marginRight: '8px',
						}
					}),
					h('span',data.title)
				]),
				h('span', {
					style:{
						display: 'inline-block',
						marginLeft:'150px'
					}
				},data.url),
				h('span', {
					style: {
						display: 'inline-block',
						float: 'right',
						marginRight: '32px',
					}
				}, [
					h('Button', {
						props: Object.assign({}, this.buttonProps, {
							icon: 'ios-add',
							type: 'success',
							size: 'small',
							//ghost:'ghost',
						}),
						style: {
							marginRight: '8px'
						},
						on: {
							click: () => { this.menu_add(root, node, data) }
						}
					},'新增'),
					h('Button', {
						props: Object.assign({}, this.buttonProps, {
							icon: 'ios-create-outline',
							type: 'primary',
							size: 'small',
							//ghost:'ghost',
						}),
						style: {
							marginRight: '8px'
						},
						on: {
							click: () => { this.menu_upd(root, node, data) }
						}
					},'修改'),
					h('Button', {
						props: Object.assign({}, this.buttonProps, {
							icon: 'ios-remove',
							type: 'error',
							size: 'small',
							//ghost:'ghost',
						}),
						on: {
							click: () => { this.menu_del(root, node, data) }
						}
					},'删除')
				])
			]);
		},
		menu_add:function(root, node, data){
			var newnode={
				p_menuname:node.node.menuname,
				id:"",
				url:'',
				menuname:'',
				pid:data.id,
				isEnable:true,
			};
			this.menu_form=newnode;
			this.is_show=true;
		},
		menu_upd:function(root, node, data){
			this.menu_form=common.deepCopy(data);
			this.menu_form.p_menuname=root[node.parent].node.menuname;
			this.is_show=true;
		},
		menu_del:function(root, node, data){
			this.menu_form.id=data.id;
			this.menu_form.status=-1;
			menu.submit();
		},
		submit:function(){
			axios({
				url: ctxPath + 'sys/menu/addOrUpdate',
				method: "post",
				params:this.menu_form}).then(res => {
				return res.data
			}).then( data => {
				this.getMenus();
				this.$Message.success(data.msg);
			})
		},
		chooseIcon:function(){
			menu.is_show_icon=true;
		},
		//弃用方法 ztt
		_expandRow:function(params){
			console.log("-------------");
			//判断当前行是否展开，如果未展开，执行以下方法，先展开再请求接口加载到tabledata中当前data index 后
			if(!this.menu_datas[params.index].isDown){
				axios({
					url: ctxPath + 'sys/menu/getAllMenu',
					method: "post",
					params:{pid:this.menu_datas[params.index].id}
				}).then(res => {
					return res.data
				}).then( data => {
					this.menu_datas[params.index].isDown = true;
					let newArrayData = data.data;
					this.menu_datas[params.index].totals = newArrayData.length; //将展开操作查询到的数据总条数加到当前行数据的totals上
					newArrayData.map( item =>{
						item.isExpand = true;
						item.upLevelIndex = params.index;
					});
					newArrayData.map( (value, key) =>{
						this.menu_datas.splice(params.index + key + 1, 0, value);
					});
				}).catch(function (error) {
					menu.$Message.error(error.response.data.message);
				});
			}else{//如果当前行已展开，则隐藏
				this.menu_datas[params.index].isDown = false
				this.menu_datas.splice(params.index + 1, params.row.totals)
			}
		},
		getMenus:function () {
			axios({
				url: ctxPath + 'sys/menu/getAll',
				method: "post",
				params:menu.param})
				.then(function (response) {
					menu.init(response.data.data);
				})
				.catch(function (error) {
					menu.$Message.error(error.response.data.message);
				});
		},
		init:function (data) {
			this.menu_datas=data;
			var memu_list=common.filterArrayToTree(data,0);
			var root=[{
				icon: "ios-apps",
				id: 0,
				title: "菜单管理",
				menuname:"菜单管理",
				pid: '',
				expand: true,//默认展开
				children:memu_list,
				render: (h, {root, node, data}) => {
					return h('span', {
						style: {
							display: 'inline-block',
							width: '100%',
						}
					}, [
						h('span', [
							h('Icon', {
								props: {
									type: 'ios-folder-outline'
								},
								style: {
									marginRight: '8px'
								}
							}),
							h('span', data.title)
						]),
						h('span', {
							style:{
								display: 'inline-block',
								marginLeft: '200px',
							}
						},'链接'),
						h('span', {
							style: {
								display: 'inline-block',
								float: 'right',
								marginRight: '32px',
							}
						}, [
							h('Button', {
								props: Object.assign({}, this.buttonProps, {
									icon: 'ios-add',
									type: 'success',
									size: 'small',
									//ghost:'ghost',
								}),
								style: {
								},
								on: {
									click: () => {
										menu.menu_add(root, node, data);
									}
								}
							},'新增')
						])
					]);
				},
			}];
			this.menus=root;
		}
	}
});
$(function () {
	/*menu.menu_datas=result.data;
	var memu_list=common.filterArrayToTree(result.data,0);
	var root=[{
		icon: "ios-apps",
		id: 0,
		title: "菜单管理",
		menuname:"菜单管理",
		pid: '',
		children:memu_list,
		render: (h, {root, node, data}) => {
			return h('span', {
				style: {
					display: 'inline-block',
					width: '100%',
				}
			}, [
				h('span', [
					h('Icon', {
						props: {
							type: 'ios-folder-outline'
						},
						style: {
							marginRight: '8px'
						}
					}),
					h('span', data.title)
				]),
				h('span', {
					style:{
						display: 'inline-block',
						marginLeft: '200px',
					}
				},'链接'),
				h('span', {
					style: {
						display: 'inline-block',
						float: 'right',
						marginRight: '32px',
					}
				}, [
					h('Button', {
						props: Object.assign({}, this.buttonProps, {
							icon: 'ios-add',
							type: 'primary',
							size: 'small',
						}),
						style: {
						},
						on: {
							click: () => {
								menu.menu_add(root, node, data);
							}
						}
					},'新增')
				])
			]);
		},
	}];
	menu.menus=root;*/
	menu.getMenus();
});