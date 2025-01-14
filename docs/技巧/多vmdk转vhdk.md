**多vmdk转单vmdk**

vmware-vdiskmanager.exe -r "E:\vmware_project\kali-linux-2024.2-vmware-amd64.vmwarevm\kali-linux-2024.2-vmware-amd64.vmdk" -t 0 "E:\a.vmdk"

**vmdk转vhdk**

qemu-img convert a.vmdk -O vhdx kali-linux-2024.2-vmware-amd64.vhdx