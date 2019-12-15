var vm = new Vue(
		{
			el : '#rrapp',
			data : {
				showList : true,
				title : null,
				config : {}
			},
			methods : {
				query : function() {
					vm.reload();
				},
				add : function() {
					vm.showList = false;
					vm.title = "新增";
					
				},
				update : function(event) {
					var id = getSelectedRow('ossConfigTable', 'id');
					if (id == null) {
						return;
					}
					vm.showList = false;
					vm.title = "修改";

					vm.getInfo(id)
				},
				saveOrUpdate : function(event) {
					var url = vm.config.id == null ? "/oss/config/create"
							: "/oss/config/update";
					$.ajax({
						type : "POST",
						url : rootPath + url,
						contentType : "application/json",
						data : JSON.stringify(vm.config),
						success : function(r) {
							alert(r.msg);
							vm.reload();
						}
					});
				},
				del : function(event) {
					var ids = getSelectedRows('ossConfigTable', 'id');
					if (ids == null) {
						return;
					}
					layui.use('layer', function() {
		                var layer = layui.layer;
		                layer.confirm('确定要删除选中的记录？', function(index) {
		                    layer.close(index);
							$.ajax({
								type : "POST",
								url : rootPath + "/oss/config/delete",
								data : {
									ids : ids
								},
								success : function(r) {
									alert(r.msg);
									vm.reload();
								}
							});
						});
					});
				},
				getInfo : function(id) {
					$.get(rootPath + "/oss/config/get?id=" + id, function(
							config) {
						vm.config = config;
					});
				},
				reload : function(event) {

					vm.showList = true;
					layui.use('table', function() {
						var table = layui.table;
						table.render({
							elem : '#ossConfigTable' // 选定是那个DIV
							,
							url : rootPath + '/oss/config/page-list',
							cols : [ [ {
								type : 'checkbox'
							},
							{
								field : 'appId',
								title : '应用id',
								minWidth : 100
							}, {
								field : 'bucketName',
								title : '桶名称',
								minWidth : 100
							}, {
								field : 'region',
								title : '桶区域',
								minWidth : 100
							}, {
								field : 'secretId',
								title : '密匙id',
								minWidth : 100
							},{
								field : 'secretKey',
								title : '密匙',
								minWidth : 100
							},{
								field: 'description',
								title: '存储桶描述',
								minWidth: 100
							},
							{
								field: 'type',
								title: '存储桶类型',
								minWidth: 100,
								templet: function(data){
									if(data.type == "ALIYUN"){
										return "阿里云存储桶";
									}else if(data.type == "QCLOUD"){
										return "腾讯云存储桶";
									}else if(data.type == "QINIU"){
										return "七牛云存储桶";
									}
								}
							}
							] ],
							page : true, // 开启分页
//							request : laypagerequest,
//							response : laypageresponse,
							where : $("#searchForm").serializeJSON()
						});
					});
				}

			}
		});

vm.reload();

$("#ossConfigForm").submit(function(){
	vm.saveOrUpdate();
})
