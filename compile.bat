echo delete old files
if exist target   rmdir /S /Q target
set dst_dir=..\..\..\..\10-common\version\release\ismp\cloud1\services\template
set src_name=ismp-template.jar
set dst_name=ismp-template.jar

echo �������Ŀ¼
if not exist %dst_dir%  mkdir %dst_dir%

::�����callָ�������ж���mvn������
call mvn clean package
echo ����Ŀ��
::copy /Y target\%src_name% 	%dst_dir%\%dst_name%
::call mvn clean