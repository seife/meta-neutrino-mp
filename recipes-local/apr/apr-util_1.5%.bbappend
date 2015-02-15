# this does not belong here... :-(

do_configure_append_class-native() {
	grep ^LTFLAGS ${S}/build/rules.mk
	sed -i 's,^LTFLAGS[[:space:]]*= --silent,LTFLAGS = --tag=CC --silent,' ${S}/build/rules.mk
	grep ^LTFLAGS ${S}/build/rules.mk
}
