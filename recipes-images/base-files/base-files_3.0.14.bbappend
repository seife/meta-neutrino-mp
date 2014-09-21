# PRINC is deprecated with daisy
#PRINC := "${@int(PRINC) + 1}"

BASEFILESISSUEINSTALL = "do_custom_baseissueinstall"

do_custom_baseissueinstall() {
	do_install_basefilesissue
	install -m 644 ${WORKDIR}/issue*  ${D}${sysconfdir}
	printf "Neutrino-MP image (based on Yocto Poky ${DISTRO_VERSION}) " >> ${D}${sysconfdir}/issue
	printf "Neutrino-MP image (based on Yocto Poky ${DISTRO_VERSION}) " >> ${D}${sysconfdir}/issue.net
	printf "\\\n \\\l\n" >> ${D}${sysconfdir}/issue
	echo "%h" >> ${D}${sysconfdir}/issue.net
	echo >> ${D}${sysconfdir}/issue
	echo >> ${D}${sysconfdir}/issue.net
}
