package org.dasein.cloud.azurepack.compute.vm;

import org.apache.http.client.methods.RequestBuilder;
import org.dasein.cloud.azurepack.AzurePackCloud;
import org.dasein.cloud.azurepack.compute.vm.model.WAPDiskDriveModel;
import org.dasein.cloud.util.requester.entities.DaseinObjectToJsonEntity;

/**
 * Created by vmunthiu on 2/1/2016.
 */
public class AzurePackVolumeRequests {
    private final String LIST_VOLUMES = "%s/%s/services/systemcenter/vmm/VirtualHardDisks";
    private final String LIST_VMS_WITH_VDD = "%s/%s/services/systemcenter/vmm/VirtualMachines?$expand=VirtualDiskDrives";
    private final String DISK_DRIVES = "%s/%s/services/systemcenter/vmm/VirtualDiskDrives";
    private final String DISK_DRIVE = "%s/%s/services/systemcenter/vmm/VirtualDiskDrives(ID=guid'%s',StampId=guid'%s')";

    private AzurePackCloud provider;

    public AzurePackVolumeRequests(AzurePackCloud provider){
        this.provider = provider;
    }

    public RequestBuilder listVirtualDisks(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(LIST_VOLUMES, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    public RequestBuilder listVMsWithVDD(){
        RequestBuilder requestBuilder = RequestBuilder.get();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(LIST_VMS_WITH_VDD, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber()));
        return requestBuilder;
    }

    public RequestBuilder createDiskDriver(WAPDiskDriveModel wapDiskDriveModel) {
        RequestBuilder requestBuilder = RequestBuilder.post();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(DISK_DRIVES, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber()));
        requestBuilder.setEntity(new DaseinObjectToJsonEntity<WAPDiskDriveModel>(wapDiskDriveModel));
        return requestBuilder;
    }

    public RequestBuilder deleteDiskDrive(String diskDriveId, String stampId) {
        RequestBuilder requestBuilder = RequestBuilder.delete();
        addCommonHeaders(requestBuilder);
        requestBuilder.setUri(String.format(DISK_DRIVE, this.provider.getContext().getCloud().getEndpoint(), this.provider.getContext().getAccountNumber(), diskDriveId, stampId));
        return requestBuilder;
    }

    private void addCommonHeaders(RequestBuilder requestBuilder) {
        requestBuilder.addHeader("x-ms-version", "2014-02-01");
        requestBuilder.addHeader("Accept", "application/json");
    }
}
