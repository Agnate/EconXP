package ca.agnate.EconXP;

import java.io.File;

import org.bukkit.util.config.Configuration;

public class Config {
    
    protected String filename;
    protected static File dir;
    protected EconXP plugin;
    
    
    public Config (EconXP aPlugin, String aFile) {
        // Save plugin.
        plugin = aPlugin;
        
        // Setup filename.
        filename = aFile;
        
        // Setup directory and desc info.
        dir = plugin.getDataFolder();
    }
    
    public void getData() {
        // Get the configuration file.
        Configuration config = makeConfig();
        
        // Load the config.
        config.load();
        
        // Check required properties.
        //getConfigProp_Number( config, "propname", 0 );
        //getConfigProp_Boolean( config, "propname", false );
        //getConfigProp_String( config, "propname", "" );

        // Save the file
        config.save();
    }
    public void saveData() {
        // Get the configuration file.
        Configuration config = makeConfig();
        
        // Load the config.
        config.load();
        
        // Check required properties.
        //setConfigProp_Number( config, "propname", 0 );
        //setConfigProp_Boolean( config, "propname", false );
        //setConfigProp_String( config, "propname", "" );

        // Save the file
        config.save();
    }
    
    protected void checkDir () {
        // If there is no directory, make it.
        try {
            if (!dir.exists())
                dir.mkdirs();
        } catch (Exception e) {
            System.out.println("[EconXP] Could not create directory!");
            System.out.println("[EconXP] You must manually create the plugins/EconXP/ directory!");
            return;
        }
        
        // Make sure we can read / write
        dir.setWritable(true);
        dir.setExecutable(true);
    }
    protected Configuration makeConfig () {
        // Check the directory.
        checkDir();
        
        // Create the file.
        File file = new File(dir, filename);

        // Make Configuration object
        return new Configuration(file);
    }
    
    protected Number getConfigProp_Number(Configuration aConfig, String aProperty, Number aDefault) {
        // If the property does not exist, create it.
        if (aConfig.getProperty(aProperty) == null) {
            setConfigProp_Number(aConfig, aProperty, aDefault);
        }
        
        // Return the value after its created.
        return (Number) aConfig.getProperty(aProperty);
    }
    protected Boolean getConfigProp_Boolean(Configuration aConfig, String aProperty, Boolean aDefault) {
        // If the property does not exist, create it.
        if (aConfig.getProperty(aProperty) == null) {
            setConfigProp_Boolean(aConfig, aProperty, aDefault);
        }
        
        // Return the value after its created.
        return (Boolean) aConfig.getProperty(aProperty);
    }
    protected String getConfigProp_String(Configuration aConfig, String aProperty, String aDefault) {
        // If the property does not exist, create it.
        if (aConfig.getProperty(aProperty) == null) {
            setConfigProp_String(aConfig, aProperty, aDefault);
        }
        
        // Return the value after its created.
        return (String) aConfig.getProperty(aProperty);
    }
    
    protected void setConfigProp_Number(Configuration aConfig, String aProperty, Number aValue) {
        // Set property.
        aConfig.setProperty(aProperty, aValue);
    }
    protected void setConfigProp_Boolean(Configuration aConfig, String aProperty, Boolean aValue) {
     // Set property.
        aConfig.setProperty(aProperty, aValue);
    }
    protected void setConfigProp_String(Configuration aConfig, String aProperty, String aValue) {
     // Set property.
        aConfig.setProperty(aProperty, aValue);
    }
}
