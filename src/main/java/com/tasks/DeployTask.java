package com.tasks;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import com.domain.MultiPleDeploymentDO;
import com.services.component.FDDeployCompService;

public class DeployTask implements Runnable {

	private String orgId;
	private String token;
	private String serverURL;
	private String refreshToken;
	private String metadataLogId;
	private boolean isValidate;
	List<MultiPleDeploymentDO> multiPleDeploymentDOs;

	public DeployTask(String orgId, String token, String serverURL,
			String refreshToken, String metadataLogId) {
		this.metadataLogId = metadataLogId;
		this.orgId = orgId;
		this.token = token;
		this.refreshToken = refreshToken;
		this.serverURL = serverURL;
	}

	public DeployTask(String orgId, String token, String serverURL,
			String refreshToken, String metadataLogId, boolean isValidate) {
		this.metadataLogId = metadataLogId;
		this.orgId = orgId;
		this.token = token;
		this.refreshToken = refreshToken;
		this.serverURL = serverURL;
		this.isValidate = isValidate;
	}

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	@Override
	public void run() {
		String errors = null;
		boolean errorFlag = false;
		try {
			
			
			aMethod(getOrgId(), getToken(), getServerURL(),
					getRefreshToken(), getMetadataLogId(), isValidate());

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
						+ getMetadataLogId());
			}
		}
	}

	public synchronized void aMethod(String orgId, String token, String serverURL,
			String refreshToken, String metadataLogId, boolean isValidate)		{
		
		System.out.println("MetadataLogId.." + getMetadataLogId());
		FDDeployCompService deployService = new FDDeployCompService();
		deployService.deploy(orgId, token, serverURL,
				refreshToken, metadataLogId,isValidate);

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

}