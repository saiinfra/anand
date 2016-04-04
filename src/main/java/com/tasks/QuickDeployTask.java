package com.tasks;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import com.domain.MultiPleDeploymentDO;
import com.services.component.FDQuickDeployCompService;

public class QuickDeployTask implements Runnable {

	String orgId;
	String token;
	String serverURL;
	String refreshToken;
	String metadataLogId;
	boolean isValidate;
	List<MultiPleDeploymentDO> multiPleDeploymentDOs;
	String recentValidationId;

	public QuickDeployTask(String orgId, String token, String serverURL,
			String refreshToken, String metadataLogId) {
		this.metadataLogId = metadataLogId;
		this.orgId = orgId;
		this.token = token;
		this.refreshToken = refreshToken;
		this.serverURL = serverURL;
	}

	public QuickDeployTask(String orgId, String token, String serverURL,
			String refreshToken, String metadataLogId,
			List<MultiPleDeploymentDO> multiPleDeploymentDOs,
			boolean isValidate, String recentValidationId) {
		this.metadataLogId = metadataLogId;
		this.orgId = orgId;
		this.token = token;
		this.refreshToken = refreshToken;
		this.serverURL = serverURL;
		this.multiPleDeploymentDOs = multiPleDeploymentDOs;
		this.isValidate = isValidate;
		this.recentValidationId = recentValidationId;
	}

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	@Override
	public void run() {
		int count = 0;
		String errors = null;
		boolean errorFlag = false;
		try {
			for (Iterator iterator = multiPleDeploymentDOs.iterator(); iterator
					.hasNext();) {
				MultiPleDeploymentDO multiPleDeploymentDO = (MultiPleDeploymentDO) iterator
						.next();
				FDQuickDeployCompService deployService = new FDQuickDeployCompService();
				deployService.deployRecentValidation(
						multiPleDeploymentDO.getBaseOrg(),
						multiPleDeploymentDO.getBaseOrgToken(),
						multiPleDeploymentDO.getBaseOrgURL(),
						multiPleDeploymentDO.getRefreshToken(),
						multiPleDeploymentDO.getMetadataLog(), isValidate(),
						multiPleDeploymentDO.getValidationId());
				count++;

			}

		} catch (Exception e) {
			errorFlag = true;
			StringWriter lerrors = new StringWriter();
			e.printStackTrace(new PrintWriter(lerrors));
			errors = lerrors.toString();
		} finally {
			if (errorFlag) {
				System.out.println("Deploy Operation Complete for requestId: "
						+ getMetadataLogId() + "\nWith Errors: " + errors);
			} else {
				System.out.println("Deploy Operation Complete for requestId: "
						+ count);
			}
		}
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getMetadataLogId() {
		return metadataLogId;
	}

	public void setMetadataLogId(String metadataLogId) {
		this.metadataLogId = metadataLogId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public List<MultiPleDeploymentDO> getMultiPleDeploymentDOs() {
		return multiPleDeploymentDOs;
	}

	public void setMultiPleDeploymentDOs(
			List<MultiPleDeploymentDO> multiPleDeploymentDOs) {
		this.multiPleDeploymentDOs = multiPleDeploymentDOs;
	}

	public String getRecentValidationId() {
		return recentValidationId;
	}

	public void setRecentValidationId(String recentValidationId) {
		this.recentValidationId = recentValidationId;
	}

}