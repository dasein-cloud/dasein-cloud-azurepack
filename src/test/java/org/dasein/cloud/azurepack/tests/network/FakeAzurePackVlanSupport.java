/*
 *  *
 *  Copyright (C) 2009-2015 Dell, Inc.
 *  See annotations for authorship information
 *
 *  ====================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  ====================================================================
 *
 */

package org.dasein.cloud.azurepack.tests.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.network.AzurePackNetworkSupport;

/**
 * Created by Jane Wang on 10/20/2015.
 *
 * @author Jane Wang
 * @since 2015.09.1
 */
public class FakeAzurePackVlanSupport extends AzurePackNetworkSupport {

	private String internetGatewayId;
	
	public FakeAzurePackVlanSupport(AzurePackCloud provider) {
		this(provider, null);
	}
	
	public FakeAzurePackVlanSupport(AzurePackCloud provider, String internetGatewayId) {
		super(provider);
		this.internetGatewayId = internetGatewayId;
	}
	
	@Override
	public @Nullable String getAttachedInternetGatewayId(@Nonnull String vlanId) throws CloudException, InternalException {
		return this.internetGatewayId;
	}

}
