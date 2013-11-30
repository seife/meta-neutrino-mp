FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# PRINC := "${@int(PRINC) + 1}"
PR := "${PR}.1"

do_install_append() {
	# we need to create /dev/fb/0 symlink, so the /dev/fb symlink must not exist
	sed -i 's/^\(KERNEL=="fb0".*\)/# \1/' ${D}${sysconfdir}/udev/rules.d/localextra.rules
}
