// JavaScript Document

//CapsLock状态
var _capslk = 0;		//Caps Lock状态
var art_line = 1;		//当前显示数组
var art_num = 0;		//当前数组中自己个字
var last_code = 0;		//最后一个错误code
var start_time = 0;		//开始时间
var time_cha = 0;		//时间差毫秒数
var typing_num = 0; 		//打字总数
var all_num = 0; 		//打字总数
var error_num = 0; 		//错误字数
var right_num = 0;		//初始正确率
var jindu = 0;		//初始进度
var sudu_num = 0;		//初始速度
var _jishi = null;		//计时标记
//charCodeAt(0)

//alert(key_data);
if (key_data != '') {
	show_key();
	jishi();
	for (var i = 1; i < key_data.length; i++) {
		all_num += key_data[i].length;
	}
}

//计时
function jishi() {
	if (start_time > 0) {
		time_cha = get_time() - start_time;
		sudu_num = Math.round(typing_num / time_cha * 60000);
	}
	if (typing_num > 0) {
		right_num = Math.round((1 - error_num / typing_num) * 10000) / 100;
		jindu = Math.round((typing_num - error_num) / all_num * 10000) / 100;
	}
	document.getElementById('shijian').innerHTML = '时间：' + get_date(time_cha);
	document.getElementById('sudu').innerHTML = '速度：' + sudu_num + ' KMP';
	document.getElementById('right_num').innerHTML = '正确率：' + right_num + ' %';
	document.getElementById('jindu').innerHTML = '进度：' + jindu + ' %';
	_jishi = setTimeout("jishi()", 100);	

	localStorage.setItem("shijian",this.get_date(time_cha))
	localStorage.setItem("sudu",this.sudu_num)
	localStorage.setItem("rightNum",this.right_num)
	localStorage.setItem("jindu",this.jindu)
}

//格式化时间
function get_date(time) {
	var t = new Date(time);
	return t.getMinutes() + '分' + t.getSeconds() + '秒';
}

//获取时间戳
function get_time() {
	var t = new Date();
	return t.getTime();
}

//显示键盘按键
function show_key() {
	if (key_data[art_line]) {
		var key_code = key_data[art_line].charCodeAt(art_num);
		show_text();
		set_on(key_code, 'on', 1);
		set_shift(key_code);		//设置SHIFT
	} else {
		clearTimeout(_jishi);
		var art_text = document.getElementById('art_text');
		art_text.style.paddingLeft = '0px';
		art_text.innerHTML = '<h3>O(∩_∩)O哈哈~ 恭喜你已经完成本次测试！</h3>';
		document.getElementById('jindu').innerHTML = '进度：100 %';
	}
}

//显示速度，进度，正确率等
function show_sudu() {
	var shijian = document.getElementById('shijian');
}

//设置shift状态
function set_shift(code) {
	var left = /^(126|33|64|35|36|37|81|87|69|82|84|65|83|68|70|71|90|88|67|86|66)$/;
	var right = /^(94|38|42|40|41|95|43|89|85|73|79|80|123|125|124|72|74|75|76|58|34|78|77|60|62|63)$/;
	set_on('991', null);
	set_on('992', null);
	if (code >= 97 && code <= 122) {
		//小写字母
		if (_capslk == 1) {
			code -= 32;
			if (left.test(code)) {
				set_on('992', 'on', 1);
			} else if (right.test(code)) {
				set_on('991', 'on', 1);
			}
		}
	} else if (code >= 65 && code <= 90) {
		//大写字母
		if (_capslk != 1) {
			if (left.test(code)) {
				set_on('992', 'on', 1);
			} else if (right.test(code)) {
				set_on('991', 'on', 1);
			}
		}
	} else {
		if (left.test(code)) {
			set_on('992', 'on', 1);
		} else if (right.test(code)) {
			set_on('991', 'on', 1);
		}
	}
}

//显示文本
function show_text() {
	if (key_data[art_line]) {
		var art_text = document.getElementById('art_text');
		art_text.style.paddingLeft = Math.round((990 - key_data[art_line].length * 65) / 2) + 'px';
		var data_html = '';
		for (var i = 0; i < key_data[art_line].length; i++) {
			data_html += '<div class="b_' + (i == art_num ? 'on' : 'alt') + '"><ul><li>' + key_data[art_line].substr(i, 1) + '</li></ul></div>';
		}
		art_text.innerHTML = data_html;
	}
}

//获取按键Code
function checkKey(evt) {
	if (key_data[art_line]) {
		var code = get_keyCode(evt);
		var key_code = key_data[art_line].charCodeAt(art_num);
		//计时
		if (start_time == 0) {
			start_time = get_time();
		}
		set_on(last_code, null);
		last_code = 0;
		typing_num++;
		if (code == key_code) {
			//正确
			set_on(code, null);
			if (art_num < key_data[art_line].length - 1) {
				art_num++;
			} else {
				art_num = 0;
				art_line++;
			}
		} else {
			//错误
			error_num++;
			if (code_code(code) != code_code(key_code)) {
				last_code = code;
				set_on(code, 'err', 1);
			}
		}
		show_key();
	}
	return false;
}


//检查键盘按下
function checkDown(evt) {
	var evt = evt ? evt : (window.event ? window.event : null);
	var code = evt.which ? evt.which : evt.keyCode;
	if (code == 8 || code == 9 || code == 32) {
		//禁止BackSpace Tab 空格
		checkKey(evt);
		return false;
	}
	if (code == 20) {
		if (_capslk == 1) {
			_capslk = 0;
			set_on(20, null);
		} else {
			_capslk = 1;
			set_on(20, 'on', 1)
		}
	}
}

//获取按键code，并设置CapsLock状态
function get_keyCode(evt) {
	var evt = evt ? evt : (window.event ? window.event : null);
	var code = evt.which ? evt.which : evt.keyCode;
	var valueShift = evt.shiftKey ? evt.shiftKey : ((code == 16) ? true : false);
	//设置CapsLock状态
	if ((code >= 65 && code <= 90 && !valueShift) || (code >= 97 && code <= 122 && valueShift)) {
		_capslk = 1;
		set_on(20, 'on', 1);
	} else if ((code >= 65 && code <= 90) || (code >= 97 && code <= 122)) {
		_capslk = 0;
		set_on(20, null);
	}
	return code;
}

//设置按键颜色
function set_on(key_code, on, keep) {
	key_code = code_code(key_code);
	var a = document.getElementById('a_' + key_code);
	if (on == 'on' || on == 'err') {
		if (a) {
			if (on == 'on') {
				var class_test = /(_on)+$/i;
				if (!class_test.test(a.className)) {
					a.className += '_on';
				}
			} else {
				var class_test = /(_err)+$/i;
				if (!class_test.test(a.className)) {
					a.className += '_err';
				}
			}
			if (!keep) {
				setTimeout(function () { set_on(key_code, null) }, 150);
			}
		}
	} else {
		if (a) {
			a.className = a.className.replace(/(_on|_err)+$/i, '');
		}
	}
	return true;
}

//将两键位code转换为一个
function code_code(key_code) {
	if (key_code >= 65 && key_code <= 90) {
		key_code += 32;
	}
	var replace_1 = new Array(126, 33, 64, 35, 36, 37, 94, 38, 42, 40, 41, 95, 43, 60, 62, 63, 58, 34, 123, 125, 124)
	var replace_2 = new Array(96, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 45, 61, 44, 46, 47, 59, 39, 91, 93, 92);
	for (var i = 0; i < replace_1.length; i++) {
		key_code = key_code == replace_1[i] ? replace_2[i] : key_code;
	}
	return key_code;
}

//跳转菜单
