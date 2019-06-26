// 时间格式化绑定
Date.prototype.format = function(str) {
	var stemp;
	var dateyear = this.getFullYear();
	var datemonth = this.getMonth()+1;
	var dateday = this.getDate();
	var datehour = this.getHours();
	var dateminute = this.getMinutes();
	var datesecond = this.getSeconds();
	var datemillisecond = this.getMilliseconds();
	//js中string.replace(str, "") ----> 只替换1个
	//    string.replace(/str/g, "") ----> 全局替换
	stemp = str.replace(/[Yy]{2,4}/g, dateyear)
		.replace(/[M]{1,2}/g, datemonth)
		.replace(/[Dd]{1,2}/g, dateday)
		.replace(/[Hh]{1,2}/g, datehour)
		.replace(/[m]{1,2}/g, dateminute)
		.replace(/[s]{1,2}/g, datesecond)
		.replace(/(ms){1}/g, datemillisecond);
	return stemp;
};




var common={};
//将集合变成树形,适合menu
common.filterArray=function(data, parent) {
	var tree = [];
	var temp;
	for (var i = 0; i < data.length; i++) {
		if (data[i].pid == parent) {
			var obj = data[i];
			temp = this.filterArray(data, data[i].id);
			if (temp.length > 0) {
				obj.sub = temp;
			}
			tree.push(obj);
		}
	}
	return tree;
}
common.deepCopy=function(data){
	var obj={};
	obj=JSON.parse(JSON.stringify(data)); //this.templateData是父组件传递的对象
	return obj;
}
//将集合变成树形,适合tree
common.filterArrayToTree=function(data, parent) {
	var tree = [];
	var temp;
	for (var i = 0; i < data.length; i++) {
		if (data[i].pid == parent) {
			//下面属于引用
			var obj=data[i];
			obj.title=data[i].menuname;
			temp = this.filterArrayToTree(data, data[i].id);
			if (temp.length > 0) {
				obj.expand=true;
				obj.children= temp;
			}
			tree.push(obj);
		}
	}
	return tree;
}

// 根据对象转为 url字符串 **=**&&**=**
common.stringify = function (data) {
	var ret = '';
	for (var it in data) {
		var value = data[it];
		if(value !== "" && value != undefined && value != null) {
			ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
		} else {
			ret += encodeURIComponent(it) + '=&';
		}
	}
	return ret.substring(0, ret.length-1);
};
common.getPinyin=function (str) {
	var pinyin='';
	//axios 不能使用异步, 使用jQuery
	$.ajax({
		url: ctxPath + 'sys/pinyin/getPinyin',
		type:'POST', //GET
		async:false,    //或false,是否异步
		data:{
			str:str
		},
		success:function(data){
			pinyin=data;
		},
	});
	return pinyin;
}
common.getAlpha=function (str) {
	var pinyin='';
	//axios 不能使用异步, 使用jQuery
	$.ajax({
		url: ctxPath + 'sys/pinyin/getAlpha',
		type:'POST', //GET
		async:false,    //或false,是否异步
		data:{
			str:str
		},
		success:function(data){
			pinyin=data;
		},
	});
	return pinyin;
}