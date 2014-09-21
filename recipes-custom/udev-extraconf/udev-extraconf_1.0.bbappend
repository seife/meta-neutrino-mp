FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
DEPENDS_append = "update-rc.d-native"

# PRINC is deprecated in daisy
#PRINC := "${@int(PRINC) + 1}"

SRC_URI += " \
	file://media-tmpfs.sh \
	file://udev-blockdev.sh \
"

do_install_append() {
	# we need to create /dev/fb/0 symlink, so the /dev/fb symlink must not exist
	sed -i 's/^\(KERNEL=="fb0".*\)/# \1/' ${D}${sysconfdir}/udev/rules.d/localextra.rules
	install -D -m 0755 ${WORKDIR}/media-tmpfs.sh ${D}${sysconfdir}/init.d/aa_media-tmpfs.sh
	install -D -m 0755 ${WORKDIR}/udev-blockdev.sh ${D}${sysconfdir}/init.d/zz_udev-blockdev.sh
	# needs to run after S02sysfs.sh and before S03udev -> call it aa_media...
	update-rc.d -r ${D} aa_media-tmpfs.sh start 03 S .
	# needs to run after S03udev -> zz_udev...
	update-rc.d -r ${D} zz_udev-blockdev.sh start 03 S .
}

FILES_${PN} += " \
	${sysconfdir}/init.d \
	${sysconfdir}/rcS.d \
"
