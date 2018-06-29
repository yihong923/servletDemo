/**
 *
 */
package com.bamboocloud.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bamboocloud.im.gson.Gson;
import com.bamboocloud.im.integration.IntegrationInstance;
import com.bamboocloud.im.integration.IntegrationMain;
import com.bamboocloud.im.integration.client.IntegrationRunnable;
import com.bamboocloud.im.integration.client.api.AbstractOperationApi01;

/**
 * @author Sammy
 *
 */
public class ObjectSyn extends AbstractOperationApi01 {

	public ObjectSyn(String systemCode) {
		super(systemCode);
		// TODO Auto-generated constructor stub
	}

	public static final String propertyNameForCsvFileStorePath = IntegrationInstance.PROPERTY_NAME_PREFIX
			+ "csvFileStorePath";

	private static Gson gson = new Gson();

	private static String objectCodeForAccount = "LOANACC";//LOANACC acc
	private static String objectCodeForOrganization = "LOANORG";
	private static String objectCodeForLookUp = "loan.systemrole";
	private static String objectCodeForRole = "LOANROLE";
	private static String objectCodeForMenu = "LOANRIGHT";
	private static String objectCodeForMenuAdmin = "busadmin_1";

	private Map<String, File> csvFileMap = new LinkedHashMap<String, File>();
	private Map<String, List<String>> csvTitleMap = new LinkedHashMap<String, List<String>>();
	private Charset charset = Charset.forName("UTF-8");

	@Override
	public void initialize(final Map<String, OperationAttributeSchema> schemas, final boolean debug) throws Exception {
		super.initialize(schemas, debug);
		this.printMemory();
	}

	@Override
	public Map<String, Map<String, Object>> findAll(String objectCode) throws Exception {
		System.out.println("OperationApi findAll");
		System.out.println("System.out.print opearationapi findall");
		Map<String, Map<String, Object>> rows = new LinkedHashMap<String, Map<String, Object>>();

		if (objectCode.equalsIgnoreCase(objectCodeForAccount)) {
			rows = this.findAllAccount();
		} else {
			rows = this.findAllOrganization();
		}

		return rows;
	}

	/**
	 * @return
	 */
	private Map<String, Map<String, Object>> findAllOrganization() {

		OperationAttributeSchema schema = this.schemas.get(objectCodeForOrganization);
		Map<String, Map<String, Object>> datas = new LinkedHashMap<String, Map<String, Object>>();

		ArrayList list = new ArrayList();
		list.add("A10020001");
		list.add("A10020002");
		list.add("A10020003");
		list.add("A10020004");
		list.add("A10020005");
		list.add("A10020006");
		list.add("A10020007");
		list.add("A10020008");
		list.add("A10020009");
		list.add("A10020010");
		list.add("A10020011");
		list.add("A10020012");
		list.add("A10020013");
		list.add("A10020014");
		list.add("A10020015");
		list.add("A10020016");
		list.add("A10020017");
		// list.add("1-1");
		// list.add("1-2");
		// list.add("1-3");
		// list.add("1-1-1");
		// list.add("1-2-1");
		// list.add("1-2-2");

		for (int i = 0; i < list.size(); i++) {
			String code = list.get(i).toString();
			Map<String, Object> data = new LinkedHashMap<String, Object>();

			for (Entry<String, OperationAttributeSchema.OperationAttribute> schemaEntry : schema.attributes
					.entrySet()) {
				if (schemaEntry.getValue().code.equalsIgnoreCase("code")) {
					data.put(schemaEntry.getKey(), code);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("name")) {
					data.put(schemaEntry.getKey(), code);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("fullname")) {
					data.put(schemaEntry.getKey(), code);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("isDisabled")) {
					data.put(schemaEntry.getKey(), false);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("parent")) {

					// String[] codePath = code.split("-");
					// if (codePath != null && codePath.length > 1) {
					// String parent = null;
					// for (int j = 0; j < codePath.length - 1; j++)
					// parent = parent == null ? codePath[j] : parent + "-" +
					// codePath[j];
					// data.put(schemaEntry.getKey(), parent);
					// }
					if (!code.equalsIgnoreCase("A1002001")) {

					}
				}

				datas.put(code, data);
			}

		}
		return datas;
	}

	/**
	 * @return
	 */
	private Map<String, Map<String, Object>> findAllAccount() {
		OperationAttributeSchema schema = this.schemas.get(objectCodeForAccount);
		Map<String, Map<String, Object>> datas = new LinkedHashMap<String, Map<String, Object>>();

		for (int i = 0; i < 1000; i++) {
			Map<String, Object> data = new LinkedHashMap<String, Object>();

			for (Entry<String, OperationAttributeSchema.OperationAttribute> schemaEntry : schema.attributes
					.entrySet()) {
				String str = i + "";
				if (schemaEntry.getValue().code.equalsIgnoreCase("username")) {
					data.put(schemaEntry.getKey(), "username" + str);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("fullname")) {
					data.put(schemaEntry.getKey(), "fullname" + str);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("isDisabled")) {
					data.put(schemaEntry.getKey(), false);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("isLocked")) {
					data.put(schemaEntry.getKey(), false);
				}
				if (schemaEntry.getValue().code.equalsIgnoreCase("SYSTEMROLE")) {
					data.put(schemaEntry.getKey(), "SYSTEMROLE" + str);
				}

				datas.put("username" + str, data);
				//System.out.println("######str=" + schemaEntry.getKey());
			
			}

		}
		return datas;
	}

	@Override
	public String create(String objectCode, Map<String, Object> data){
		System.out.println("OperationApi create ：" + objectCode);
		String newGuid = null;
		System.out.println("data is ：：：：" + data.toString());
		if (objectCode.equalsIgnoreCase(objectCodeForAccount)) {
			newGuid = data.get("username").toString();
			saveToFile(data.toString());
		} else if (objectCode.equalsIgnoreCase(objectCodeForOrganization)) {
			newGuid = data.get("code").toString();
		} else if(objectCode.equalsIgnoreCase(objectCodeForRole) || objectCode.equalsIgnoreCase(objectCodeForMenu) || objectCode.equalsIgnoreCase(objectCodeForMenuAdmin)) {
			saveToFile(data.toString());
			newGuid = data.get("code").toString();
		}
		System.out.println("newGuid=" + newGuid);
		
		return newGuid;

	}

	private void saveToFile(String data){
		try {
			File file = new File("d:\\user.txt");
			/*if(!file.exists()) {
				file.createNewFile();
			}*/
			FileOutputStream writer = new FileOutputStream(file, true);
			writer.write(data.getBytes());
			writer.write(",".getBytes());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public String update(String objectCode, String guid, Map<String, Object> data) throws Exception {
		System.out.println("OperationApi update");

		System.out.println("data is ：：：：" + data.toString());
		String newGuid = null;
		if (objectCode.equalsIgnoreCase(objectCodeForAccount)) {
			newGuid = data.get("username").toString();
			if (newGuid.equalsIgnoreCase("bob"))
				throw new Exception("user not is existed");
		} else if (objectCode.equalsIgnoreCase(objectCodeForOrganization)) {
			newGuid = data.get("code").toString();
		}

		return newGuid;
	}

	@Override
	public String delete(String objectCode, String guid) throws Exception {
		System.out.println("OperationApi delete, guid:" + guid);

		if (objectCode.equalsIgnoreCase(objectCodeForAccount)) {
			if (guid.equalsIgnoreCase("bob"))
				throw new Exception("user is already deleted");
		} else if (objectCode.equalsIgnoreCase(objectCodeForOrganization)) {
			// success deleted
		}
		return guid;
	}

	@Override
	public void changeStatus(String objectCode, String guid, STATUS status) throws Exception {
		System.out.println("changeStatus " + guid + " " + status.name());
		if (objectCode.equalsIgnoreCase(objectCodeForAccount)) {
			if (guid.equalsIgnoreCase("bob"))
				throw new Exception("user is not existed!");
		} else if (objectCode.equalsIgnoreCase(objectCodeForOrganization)) {
			// success change status
		}
	}

	private Map<String, Object> transRow2Data(Map<String, String> row, OperationAttributeSchema schema)
			throws Exception {
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		for (Entry<String, OperationAttributeSchema.OperationAttribute> entry : schema.attributes.entrySet()) {
			Object val = null;
			String str = row.get(entry.getKey());
			System.out.println("key=" + str);
			if (str != null) {
				switch (entry.getValue().type) {
				case STRING:
					val = str;
					break;
				case INTEGER:
					val = Integer.valueOf(str);
					break;
				case LONG:
					val = Long.valueOf(str);
					break;
				case DATE:
					val = "".equals(str) ? null : gson.fromJson(gson.toJson(str), entry.getValue().type.getClazz());
					break;
				case DATETIME:
					val = "".equals(str) ? null : gson.fromJson(gson.toJson(str), entry.getValue().type.getClazz());
					break;
				case TIMESTAMP:
					val = "".equals(str) ? null : gson.fromJson(gson.toJson(str), entry.getValue().type.getClazz());
					break;
				case BOOLEAN:
					val = "".equals(str) ? false : Boolean.valueOf(str);
					break;
				case TEXT:
					val = str;
					break;
				case BINARY:
					val = "".equals(str) ? null : gson.fromJson(gson.toJson(str), entry.getValue().type.getClazz());
					break;
				default:
					break;
				}
			}

			data.put(entry.getKey(), val);
		}
		return data;
	}

	

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.bamboocloud.im.integration.client.api.AbstractOperationApi01#getByGuid
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> getByGuid(String objectCode, String guid) throws Exception {
		// TODO Sammy Auto-generated method stub
		return null;
	}

	private void printMemory() {
		int mb = 1024 * 1024;

		// Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		System.out.println("##### Heap utilization statistics [MB] #####");

		// Print used memory
		System.out.println("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);

		// Print free memory
		System.out.println("Free Memory:" + runtime.freeMemory() / mb);

		// Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / mb);

		// Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / mb);
	}
	
	public static void main(String[] args) {
		String file = "F:\\ehom\\workplace\\Test\\src\\com\\bamboocloud\\im\\client\\test\\integration.properties";
		// IntegrationMain.start(file);
		// System.exit(0);
		for (int i = 0; i < 1; i++) {
			try {
				// Thread.sleep(1000 * 20);
				System.out.println("手工下拉");
				IntegrationMain.startOnce(file, IntegrationRunnable.METHOD.PULL, 1);
				
				/*System.out.println("手工回收");
				IntegrationMain.startOnce(file, IntegrationRunnable.METHOD.PUSH, 1);
				System.out.println("手工下拉回收完成");*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
