<div class="switch-bg">
		<div id="switch-bg-list">
			<div class="switch-loading" style="display: none;"><!-- 
				 <img id="bgimg" src="/img/bg_img/bg2.jpg" alt="" class="switch-chg-bg " /> -->
			</div> 
			<a target="_blank" href="javascript:void(0)" id="SYSTEM_authinfo" style="font-size: 13px;">{{SYSTEM_authinfo}} </a>
		</div>
</div>
	<script src="/js/plugins/swiper/swiper.js"></script>
	<script type="text/javascript" src="/js/plugins/layui/layui.js"></script>
	<script src="/js/system/login_reg_fPass.js"></script>
	<script src="/js/canvas.js"></script>
	<script type="text/javascript">
        var app2 = new Vue({
            el : '#SYSTEM_authinfo',
            data : {
                SYSTEM_authinfo : ''
            },
            created : function() {
                var url = "/web_domain";
                var _self = this;
                $.post(url, function(data) {
                    _self.SYSTEM_authinfo = data;
                })
            }
        });
    </script>
	<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
	<script>
        //百度统计
        var _hmt = _hmt || [];
        (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?577038104fe0a8e00d90faee2f037aac";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
	<script>
        //腾讯统计
        var _mtac = {};
        (function() {
            var mta = document.createElement("script");
            mta.src = "http://pingjs.qq.com/h5/stats.js?v2.0.4";
            mta.setAttribute("name", "MTAH5");
            mta.setAttribute("sid", "500553015");
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(mta, s);
        })();
    </script>
	<script type="text/javascript" src="http://tajs.qq.com/stats?sId=64208831" charset="UTF-8"></script>