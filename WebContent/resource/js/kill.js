var seckill = {
    //封装秒杀相关ajax的url
	URL: {
		now:function(){
            return '/seckill/time/now';  
        },  
        exposer:function(seckillId){  
            return '/seckill/'+seckillId+'/exposer';  
        },  
        kill:function(seckillId,md5){  
            return '/seckill/'+seckillId+'/'+md5+'/execution';  
        }
	},
	handlerSeckillKill : function(seckillId,node){
		//获取秒杀，控制逻辑实行秒杀
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀！</button>');
		//在回调函数中执行秒杀操作
        $.post(seckill.URL.exposer(seckillId),{},function(result){
        	if(result && result['success']){  
                var exposer = result['data'];  
                if(exposer['exposed']){
                	//开启秒杀
                    var md5 = exposer['md5'];
                    //获取秒杀地址
                    var killUrl = seckill.URL.kill(seckillId,md5);
                    console.log("killUrl:"+killUrl);
                    //绑定一次秒杀事件
                    $('#killBtn').one('click',function(){
                    	//执行秒杀请求
                    	//1.先禁用按钮
                        $(this).addClass('disabled');
                        //2.发送请求
                        $.post(killUrl,{},function(data){  
                            if(data && data['success']){  
                                var result = data['data'];  
                                var state = result['state'];  
                                var stateInfo = result['stateInfo']; 
                                //3.显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>');  
                            }  
                        });  
                    });  
                    node.show();  
                } else {  
                	//未开启秒杀
                    var now = exposer['now'];  
                    var start = exposer['start'];  
                    var end = exposer['end'];  
                    seckill.countdown(seckillId,now,start,end);  
                }  
            } else {
            	console.log("data"+data);
            }
        });
		
	},
	validatePhone: function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	countdown:function(seckillId,nowTime,startTime,endTime){  
        var seckillBox = $('#seckill-box');  
        if(nowTime > endTime){
        	//秒杀已经结束了
        	seckillBox.html('秒杀结束！');
        }else if(nowTime < startTime) {
        	//秒杀还没有开始
        	var killTime = new Date(Number(startTime)+Number(1000));
            seckillBox.countdown(killTime,function(event){  
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');  
                seckillBox.html(format);  
            }).on('finish.countdown',function(){//时间完成后的回调
            	//获取秒杀，控制逻辑实行秒杀
                seckill.handlerSeckillKill(seckillId,seckillBox);  
            });  
        }else{
        	//秒杀开放中
        	seckill.handlerSeckillKill(seckillId,seckillBox);
        } 
    }, 
	//详情页秒杀逻辑
	detail: {
		//详情页初始化
		init : function(params){
			//手机验证和登录，计时交互
			//规划我们的交互流程
			//在cookie中查找手机号
			var killPhone = $.cookie('killPhone');
			//验证手机号
			if(!seckill.validatePhone(killPhone)){
				//绑定phone
				//控制输出
				var killPhoneModel = $('#killPhoneModel');
				killPhoneModel.modal({
					show:true,//显示弹出层
				    backdrop:'static',//禁止位置关闭
				    keyboard:false//关闭键盘事件
				});
				$('#killPhoneBtn').click(function(){
					var inputPhone = $('#killPhoneKey').val();
					//console.log(inputPhone);
					if(seckill.validatePhone(inputPhone)){
						//手机号写入cookie
						$.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
						//刷新页面
						window.location.reload();
					}else{
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
					}
				});
			}
			//已经登录
			//计时交互
			var startTime = params['startTime'];  
			var endTime = params['endTime'];  
			var seckillId = params['seckillId'];
			$.get(seckill.URL.now(),{},function(result){  
				if(result && result['success']){  
					var nowTime = result['data'];
					//时间判断
					seckill.countdown(seckillId,nowTime,startTime,endTime);  
				} else {  
					console.log("data"+data);  
				}  
			});
		}
		
	}
		
}