include luaposix.inc
# this might change as the year of release is in there
LIC_FILES_CHKSUM = "file://COPYING;md5=0de24acdcec9cb21ff095356966cbef2"

PR = "r1"
SRC_URI += "\
	file://fix_crosscompile.patch \
"

SRC_URI[md5sum] = "f543b8cc4fae5379ad8ed9cc1cfc2efc"
SRC_URI[sha256sum] = "d82a322cb93d25ef16ba2cbab79f6fa1d94806249542d771c2c89a5f9cc4345f"
