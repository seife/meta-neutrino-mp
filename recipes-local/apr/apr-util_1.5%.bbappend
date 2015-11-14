# this does not belong here... :-(

do_configure_append_class-native() {
	grep ^LTFLAGS ${B}/build/rules.mk
	sed -i 's,^LTFLAGS[[:space:]]*= --silent,LTFLAGS = --tag=CC --silent,' ${B}/build/rules.mk
	grep ^LTFLAGS ${B}/build/rules.mk
}
