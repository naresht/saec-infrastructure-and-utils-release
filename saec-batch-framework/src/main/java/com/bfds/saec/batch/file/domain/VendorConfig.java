package com.bfds.saec.batch.file.domain;


import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "batchFilesEntityManagerFactory")
@RooToString
public class VendorConfig implements Serializable {

    /**
     * dBUserId is used to display the User Filed in Header record as provided
     * by Deustche Bank
     */
    @Column(length = 8)
    private String deutscheBankUserId;
    
    
    /**
	 * bankNameOnDstoFile is used to display the BankName in Header record for
	 * the DSTO batch file.
	 */
    @Column(length = 30) 
    private String bankNameOnDstoFile;

    /**
     * The DB is expected to have only one VendorConfig.
     *
     * @return The current Event.
     */

    public static VendorConfig getVendorProperties() {
        List<VendorConfig> list = VendorConfig.findAllVendorConfigs();
        if (list.size() == 1) {
            return list.get(0);
        }
        throw new IllegalStateException("Expected exactly one "+VendorConfig.class+" entry in DB. Found " + list.size());
    }

}
