FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "\
	file://bcm_host.pc \
	file://egl.pc \
	file://openmaxil.pc \
"

do_install_append() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/bcm_host.pc  ${D}${libdir}/pkgconfig/
    install -m 0644 ${WORKDIR}/egl.pc       ${D}${libdir}/pkgconfig/
    install -m 0644 ${WORKDIR}/openmaxil.pc ${D}${libdir}/pkgconfig/
}

FILES_${PN}-dev += " ${libdir}/pkgconfig "

