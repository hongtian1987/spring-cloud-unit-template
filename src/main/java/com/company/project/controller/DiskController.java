package com.kedacom.ismp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class DiskController {
	public void getDiskInfo(){
		 File[] roots = File.listRoots();// 获取磁盘分区列表
	        for (File file : roots) {
	            System.out.println(file.getPath() + "信息如下:");
	            long free = file.getFreeSpace();
	            long total = file.getTotalSpace();
	            long use = total - free;
	            System.out.println("空闲未使用 = " + change(free) + "G");// 空闲空间
	            System.out.println("已经使用 = " + change(use) + "G");// 可用空间
	            System.out.println("总容量 = " + change(total) + "G");// 总空间
	            System.out.println("使用百分比 = " + bfb(use, total));
	            System.out.println();
	        }
	}
	
	public void getDiskInfo2(){
		Process pro = null;
		Runtime r = Runtime.getRuntime();
		String command = "fdisk  -l";
		try {
			pro = r.exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					pro.getInputStream()));
					String line = null;
					System.out.println("目前所有文件系统的可用空间及使用情形 Filesystem Size Used Avail Use% Mounted on");
					while ((line = in.readLine()) != null) {
					// String[] Info = line.split("\\s+");
					System.out.println(in.readLine());
					}
					in.close();
					pro.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static long change(long num) {
        // return num;
        return num / 1024 / 1024 / 1024;
    }

	private static String bfb(Object num1, Object num2) {
        double val1 = Double.valueOf(num1.toString());
        double val2 = Double.valueOf(num2.toString());
        if (val2 == 0) {
            return "0.0%";
        } else {
            DecimalFormat df = new DecimalFormat("#0.00");
            return df.format(val1 / val2 * 100) + "%";
        }
    }
	
	public List<String> getLocalMAC() {
        List<String> hards = new LinkedList<String>();
        try{
        	Enumeration<NetworkInterface> nativeNetworks = NetworkInterface.getNetworkInterfaces();
            while (nativeNetworks.hasMoreElements()) {
                NetworkInterface networkCard = (NetworkInterface)nativeNetworks.nextElement();
                String cardeName = networkCard.getName().toLowerCase();
                if (cardeName.startsWith("eth") || cardeName.startsWith("br")) {// 判断是否是本地网卡
                    List<InterfaceAddress> ncAddrList = networkCard.getInterfaceAddresses();
                    if (!ncAddrList.isEmpty()) {
                        for (InterfaceAddress interfaceAddress : ncAddrList) {
                            String ip = interfaceAddress.getAddress().getHostAddress();
                            if (ip.indexOf(":") < 0) {
                                String hardAddress = calcHard(networkCard.getHardwareAddress());
                                System.out.println("本机IP:" + ip + " 对应物理地址：" + hardAddress);
                                if (null != hardAddress && !hardAddress.equals("")) {
                                    hards.add(hardAddress);
                                }
                            }
                        }
                    }
                }
            }
        }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return hards;
    }
	
	private String calcHard(byte[] hards) {
        StringBuilder sbBuilder = new StringBuilder();
        if (null != hards) {
            for (int i = 0; i < hards.length; i++) {
                String ass = Integer.toHexString(hards[i] & 0xff);
                if (ass.length() == 1) {
                    sbBuilder.append("0" + ass);
                } else {
                    sbBuilder.append(ass.toUpperCase());
                }
            }
        }
        return sbBuilder.toString();
    }
}
