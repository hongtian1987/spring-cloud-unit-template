echo delete old files
rm -rf target
dst_dir=../../../../10-common/version/release/linux64/ismp/cloud1/services/resource
src_name=ismp-resource-scloud1.jar
dst_name=ismp-resource-scloud1.jar

echo "创建相关目录"
if [ ! -d "$dst_dir" ]; then  
    mkdir -p "$dst_dir"  
fi

#必须加call指令，否则会中断在mvn操作上
mvn clean package >../../../../10-common/version/compileinfo/ismp_cloud1_linux64_resource_r.txt 2>&1;
echo "拷贝目标"
cp -f target/$src_name 	$dst_dir/$dst_name
mvn clean

