package com.ds.salesforce.dao.comp;

import java.util.List;

import com.domain.MetaBean;
import com.ds.salesforce.dao.ISFBaseDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.sforce.soap.enterprise.sobject.OFSServer__MetadataDescription__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.util.SFoAuthHandle;

/**
 * 
 * @author MetadataDescriptionDAO is Used For Performing CRUD Operations for
 *         {@link MetadataDescription__c}
 *
 */
public class MetadataDescriptionDAO implements ISFBaseDAO {

	@Override
	public List<Object> listAll(SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Object obj, SFoAuthHandle sfHandle) {
		return false;
	}

	public boolean insert(MetaBean[] deployObjArr, String metadataLogId, String sOrgId, SFoAuthHandle sfHandle) {
		int retVal = 1;

		// create the records
		OFSServer__MetadataDescription__c[] records = new OFSServer__MetadataDescription__c[deployObjArr.length];
		try {
			for (int i = 0; i < deployObjArr.length; i++) {
				// Get the name of the sObject
				OFSServer__MetadataDescription__c a = new OFSServer__MetadataDescription__c();
				a.setOFSServer__MetadataLog__c(metadataLogId);
				a.setOFSServer__Name__c(deployObjArr[i].getName());
				a.setOFSServer__Type__c(deployObjArr[i].getType());
				a.setOFSServer__OrganizationId__c(sOrgId);
				a.setOFSServer__LastModifiedById__c(deployObjArr[i].getLastModifiedById());
				a.setOFSServer__LastModifiedByName__c(deployObjArr[i].getLastModifiedByName());

				a.setOFSServer__LastModifiedDate__c(deployObjArr[i].getLastModifiedByDate());

				records[i] = a;
			}
			commit(records, sfHandle);
			if (retVal < 0) {
				return false;
			}
		} catch (Exception ce) {
			// fce.printStackTrace();
			throw new SFException(ce.toString(), SFErrorCodes.SFMetadataDescription_Insert_Error);
		}
		return false;
	}

	public int save(SObject[] sobjects, SFoAuthHandle sfHandle) {
		int retVal = 1;

		return retVal;
	}

	@Override
	public boolean update(Object obj, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object obj, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> findById(String metadataLogIdName, SFoAuthHandle sfHandle) {
		return null;
	}

	@Override
	public boolean commit(SObject[] sobjects, SFoAuthHandle sfHandle) {
		// System.out.println("SAVE--"+sfHandle.getUserId()+"--"+sfHandle.getPasswd());
		try {
			com.sforce.soap.enterprise.SaveResult[] saveResults = sfHandle.getEnterpriseConnection().create(sobjects);

			// check the returned results for any errors
			for (int i = 0; i < saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					System.out.println(
							i + ". Successfully created MetadataDescription record - Id: " + saveResults[i].getId());
				} else {
					com.sforce.soap.enterprise.Error[] errors = saveResults[i].getErrors();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < errors.length; j++) {
						sb.append(errors[j].getMessage());
						sb.append("\n");
						System.out.println("ERROR creating record: " + errors[j].getMessage());
					}
					throw new SFException(sb.toString(), SFErrorCodes.SFMetadataDescription_Insert_Error);
				}
			}
		} catch (Exception e) {
			throw new SFException(e.toString(), SFErrorCodes.SFMetadataDescription_Insert_Error);
		}
		return true;
	}

}
