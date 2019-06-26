var user_p=new Vue({
	el:'#app',
	data:{
		user:{
			newpassword:'',
			newword:'',
			password:''
		},
		user_rules:{
			renewword:[
				{required: true, message: '该项为必填项', trigger: 'blur'},
				{trigger: 'submit',validator: function (rule, value, callback) {
						if (value!== user_p.user.newpassword) {
							callback(new Error('两次密码不一致'));
						} else {
							callback();
						}
					}
				}
			],
			newpassword:[
				{required: true, message: '该项为必填项', trigger: 'blur'},
				{
					trigger: 'submit', validator: function (rule, value, callback) {
						if (value!== user_p.user.renewword) {
							callback(new Error('两次密码不一致'));
						} else {
							callback();
						}
					}
				}
			],
			password:[{required: true, message: '该项为必填项', trigger: 'blur'}],
		},
	},
	methods:{
		submit:function () {
			//验证通过提交表单
			this.$refs["user"].validate((valid)=>{
				axios({
					url: ctxPath + 'sys/user/updatepwd',
					method: "post",
					params:this.user}).then(res => {
					return res.data
				}).then( data => {
					if(data.error){
						this.$Message.error(data.error);
					}else {
						this.$Message.success(data.msg);
					}
				})
			});
		}
	}
})