package common;

import auto.framework.DataTable;
import auto.framework.Resources;
import auto.framework.TestManager;
import auto.framework.DataTable.DataTableInstance;

public class DataRepository
{
    private static InheritableThreadLocal<DataTableInstance> standardTestDataTable = new InheritableThreadLocal<DataTableInstance>();

    public static DataTableInstance testDataToBeUsed()
    {
        DataTableInstance storedValue = standardTestDataTable.get();
        if (storedValue != null)
            return storedValue;
        String dataFilePath = Resources.findResource("./src/test/resources/data/" + TestManager.Preferences.getPreference("data") + ".xlsx");
        if (dataFilePath == null)
        {
            dataFilePath = "./src/test/resources/data/Dataset.xlsx";
        }
        DataTableInstance newValue = DataTable.Load(dataFilePath);
        standardTestDataTable.set(newValue);
        return newValue;
    }
}
