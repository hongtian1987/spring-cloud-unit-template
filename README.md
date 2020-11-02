# spring-cloud-unit-template
实现微服务业务单元模板

根目录：com.company.projectname.xxx (xxx为模块名称)
1）工程启动类(ApplicationServer.java)置于com.company.projectname.xxx 包下
2）前端控制器(Controller)或者web层(web)置于com.company.projectname.xxx .controller；
3）数据服务层/逻辑处理层(Service)置于com.company.projectname.xxx .service,数据服务的实现接口；(serviceImpl)至于com.company.projectname.xxx .impl；
4）数据访问层(Dao)置于om.company.projectname.xxx .dao；
5）通过controller或者web返回去的实体类用model配置于com.company.projectname.xxx.model；
6）配置信息类(config)置于com.company.projectname.xxx .config，用于框架内的配置；
7）工具类(utils)置于com.company.projectname.xxx .utils；
8）常量接口类(constant)置于comcompany.projectname.xxx .constant；
9）使用mybaits则需要定义数据访问(mapper) 置于comcompany.projectname.xxx.mapper；
10）数据库中的表相对应的实体类(entity)置于comcompany.projectname.xxx.entity；
11）使用redis的基础封装类(redis)置于om.company.projectname.xxx.redis中；
12）针对文件操作的类(files)置于om.company.projectname.xxx.files中；


13）中间过渡类型放置在data目录下；
14）新增组件需要启动和关闭的部分放置在build目录下；


