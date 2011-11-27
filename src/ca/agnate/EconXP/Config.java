package ca.agnate.EconXP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

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
        // Create config instance.
    	YamlConfiguration config = new YamlConfiguration();
    	File file = makeConfigFile();
    	
    	// Load the configuration File.
    	try {
			config.load( file );
		}
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			EconXP.sendMsg( null, "The config file was not found while loading. Do I have write access?");
			return;
		}
    	catch (IOException e) {
			EconXP.sendMsg( null, "The config file experienced an IOException while loading. Do I have write access?");
			return;
		}
    	catch (InvalidConfigurationException e) {
    		EconXP.sendMsg( null, "Your config file was not correct when loading. Go to EconXP forum if you can't figure it out.");
			return;
		}
        
        // Check required properties.
        //getConfigProp_Number( config, "propname", 0 );
        //getConfigProp_Boolean( config, "propname", false );
        //getConfigProp_String( config, "propname", "" );

        // Save the file
        try {
			config.save( file );
		} catch (IOException e) {
			EconXP.sendMsg( null, "The config file experienced an IOException while saving after the load. Do I have write access?");
			return;
		}
    }
    public void saveData() {
    	// Create config instance.
    	YamlConfiguration config = new YamlConfiguration(); 
    	File file = makeConfigFile();
    	
    	// Load the configuration File.
    	try {
			config.load( file );
		}
    	catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			EconXP.sendMsg( null, "The config file was not found while saving. Do I have write access?");
			return;
		}
    	catch (IOException e) {
			EconXP.sendMsg( null, "The config file experienced an IOException while saving. Do I have write access?");
			return;
		}
    	catch (InvalidConfigurationException e) {
    		EconXP.sendMsg( null, "Your config file is not correct while saving. Go to EconXP and let them know there's a problem.");
			return;
		}
        
        // Check required properties.
        //setConfigProp_Number( config, "propname", 0 );
        //setConfigProp_Boolean( config, "propname", false );
        //setConfigProp_String( config, "propname", "" );

        // Save the file
        try {
			config.save( file );
		} catch (IOException e) {
			EconXP.sendMsg( null, "The config file experienced an IOException on final save. Do I have write access?");
			return;
		}
    }
    
    protected void checkDir () {
        // If there is no directory, make it.
        try {
            if (!dir.exists())
                dir.mkdirs();
        } catch (Exception e) {
            System.out.println("[EconXP] Could not create directory!");
            System.out.println("[EconXP] You must manually create the plugins/EconXP/ directory (and give me delicious access)!");
            return;
        }
        
        // Make sure we can read / write
        dir.setWritable(true);
        dir.setExecutable(true);
    }
    protected File makeConfigFile () {
        // Check the directory.
        checkDir();
        
        // Create the file.
        return new File(dir, filename);
    }
    
    protected Number getConfigProp_Number(YamlConfiguration aConfig, String aProperty, Number aDefault) {
        // If the property does not exist, create it.
        if (aConfig.get(aProperty) == null) {
            setConfigProp_Number(aConfig, aProperty, aDefault);
        }
        
        // Return the value after its created.
        return (Number) aConfig.get(aProperty);
    }
    protected Boolean getConfigProp_Boolean(YamlConfiguration aConfig, String aProperty, Boolean aDefault) {
        // If the property does not exist, create it.
        if (aConfig.get(aProperty) == null) {
            setConfigProp_Boolean(aConfig, aProperty, aDefault);
        }
        
        // Return the value after its created.
        return (Boolean) aConfig.get(aProperty);
    }
    protected String getConfigProp_String(YamlConfiguration aConfig, String aProperty, String aDefault) {
        // If the property does not exist, create it.
        if (aConfig.get(aProperty) == null) {
            setConfigProp_String(aConfig, aProperty, aDefault);
        }
        
        // Return the value after its created.
        return (String) aConfig.get(aProperty);
    }
    
    protected void setConfigProp_Number(YamlConfiguration aConfig, String aProperty, Number aValue) {
        // Set property.
        aConfig.set(aProperty, aValue);
    }
    protected void setConfigProp_Boolean(YamlConfiguration aConfig, String aProperty, Boolean aValue) {
     // Set property.
        aConfig.set(aProperty, aValue);
    }
    protected void setConfigProp_String(YamlConfiguration aConfig, String aProperty, String aValue) {
     // Set property.
        aConfig.set(aProperty, aValue);
    }
}
