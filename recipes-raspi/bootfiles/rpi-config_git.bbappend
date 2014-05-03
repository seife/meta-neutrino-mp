do_deploy_append() {
	echo  >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
	echo "# meta-neutrino-mp/recipes-raspi/bootfiles/rpi-config_git.bbappend" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
	echo "hdmi_force_hotplug=1" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
	echo "disable_overscan=1" >> ${DEPLOYDIR}/bcm2835-bootfiles/config.txt
}
