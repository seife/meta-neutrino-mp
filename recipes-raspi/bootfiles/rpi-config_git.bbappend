do_deploy_append() {
	echo  >> ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/config.txt
	echo "# meta-neutrino-mp/recipes-raspi/bootfiles/rpi-config_git.bbappend" >> ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/config.txt
	echo "hdmi_force_hotplug=1" >> ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/config.txt
	echo "disable_overscan=1" >> ${DEPLOY_DIR_IMAGE}/bcm2835-bootfiles/config.txt
}
