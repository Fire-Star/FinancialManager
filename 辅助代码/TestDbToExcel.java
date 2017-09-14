package com.cn.hnust.cache;
import java.io.File;



import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
//http://www.cnblogs.com/zyw-205520/p/3762954.html
public class TestDbToExcel {
/*
	public static void main(String[] args) {
		try {
			WritableWorkbook wwb = null;

			// 创建可写入的Excel工作簿
			String fileName = "D://book.xls";
			File file=new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			//以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);

			// 创建工作表
			WritableSheet ws = wwb.createSheet("Test Shee 1", 0);

			//查询数据库中所有的数据

			//要插入到的Excel表格的行号，默认从0开始
			Label labelId= new Label(0, 0, "编号(id)");//表示第
			Label labelName= new Label(1, 0, "姓名(name)");
			Label labelSex= new Label(2, 0, "性别(sex)");
			Label labelNum= new Label(3, 0, "薪水(num)");

			ws.addCell(labelId);
			ws.addCell(labelName);
			ws.addCell(labelSex);
			ws.addCell(labelNum);


			Label labelId_i= new Label(0, 1, "2017");
			Label labelName_i= new Label(1, 1, "张三");
			Label labelSex_i= new Label(2, 1, "男");
			Label labelNum_i= new Label(3, 1, "10000");
			ws.addCell(labelId_i);
			ws.addCell(labelName_i);
			ws.addCell(labelSex_i);
			ws.addCell(labelNum_i);


			//写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	*/
	
	
	public static void main(String args[]){
		

	        try {
	            Workbook rwb=Workbook.getWorkbook(new File("D://book.xls"));
	            Sheet rs=rwb.getSheet("Test Shee 1");//或者rwb.getSheet(0)
	            int clos=rs.getColumns();//得到所有的列
	            int rows=rs.getRows();//得到所有的行
	            
	            System.out.println("列数="+clos+" 行数="+rows);
	            for (int i = 1; i < rows; i++) {
	                for (int j = 0; j < clos; j++) {
	                    //第一个是列数，第二个是行数
	                    String id=rs.getCell(j++, i).getContents();//默认最左边编号也算一列 所以这里得j++
	                    String name=rs.getCell(j++, i).getContents();
	                    String sex=rs.getCell(j++, i).getContents();
	                    String num=rs.getCell(j++, i).getContents();
	                    
	                    System.out.println("id:"+id+" name:"+name+" sex:"+sex+" num:"+num);
	                    
	                }
	            }
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	}
}