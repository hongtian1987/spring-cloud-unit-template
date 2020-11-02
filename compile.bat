echo delete old files
if exist target   rmdir /S /Q target
set dst_dir=..\..\..\..\10-common\version\release\ismp\cloud1\services\template
set src_name=ismp-template.jar
set dst_name=ismp-template.jar

echo 创建相关目录
if not exist %dst_dir%  mkdir %dst_dir%

::必须加call指令，否则会中断在mvn操作上
call mvn clean package
echo 拷贝目标
::copy /Y target\%src_name% 	%dst_dir%\%dst_name%
::call mvn clean