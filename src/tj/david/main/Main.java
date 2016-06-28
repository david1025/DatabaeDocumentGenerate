package tj.david.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tj.david.entity.TableInfo;
import tj.david.utils.HSSF2BeanColumMatch;
import tj.david.utils.JDBCUtils;


public class Main {

	public static void main(String[] args) {
		//InputStream is;
		XSSFWorkbook workbook = null;
		try {
			//is = new FileInputStream("D:/templete.xlsx");
			workbook = new XSSFWorkbook();
			//is.close();
			
			JDBCUtils jdbcUtils = new JDBCUtils();
			
			Connection con = jdbcUtils.getConn();
			
			List<String> tables = jdbcUtils.getAllTable(con);
			
			for (String string : tables) {
				workbook.createSheet(string);
				List<TableInfo> list = new ArrayList<TableInfo>();
				TableInfo tableInfo = new TableInfo();
				tableInfo.setName("字段");
				tableInfo.setComment("说明");
				tableInfo.setType("数据类型");
				tableInfo.setMk("类型");
				list.add(tableInfo);
				List<TableInfo> list1 = getTableInfos(string);
				
				list.addAll(list1);
				HSSF2BeanColumMatch<TableInfo> h2bc = new HSSF2BeanColumMatch<TableInfo>(TableInfo.class);
				h2bc.setMatchFieldNames(new String[] { "name", "type", "comment", "mk" });		
				h2bc.setStatEndNum(0, 0 + list.size());
				h2bc.bean2xlsx(workbook, string, list);
				workbook.getSheet(string).setForceFormulaRecalculation(true); //如果有公式，需要调用此方法刷新一下
				
			}
			
			FileOutputStream  fos = null;
			fos = new FileOutputStream ("D:/aa.xlsx");
			workbook.write(fos);
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static List<TableInfo> getTableInfos(String tableName) {
		JDBCUtils jdbcUtils = new JDBCUtils();
		
		Connection con = jdbcUtils.getConn();
		
		return jdbcUtils.getTableColumn(con, "htjrk", tableName);
	}
	
}
