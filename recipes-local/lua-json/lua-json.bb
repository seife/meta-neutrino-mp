DESCRIPTION = "Simple JSON Encode/Decode in Pure Lua"
LICENSE = "CC-BY-3.0"
LIC_FILES_CHKSUM = " \
	file://JSON.lua;beginline=1;endline=16;md5=e06eabfd75688389044020b8796f0a65 \
"
HOMEPAGE = "http://regex.info/blog/lua/json/"
DEPENDS="lua5.2"

SRC_URI = " \
	file://JSON.lua \
"

inherit allarch

PV = "20141223.14"

S = "${WORKDIR}"

FILES_${PN} += "/usr/share/lua/5.2"
# no need for -dev and -dbg..
PACKAGES = "${PN}"

do_install () {
	install -d ${D}/usr/share/lua/5.2/
	install -m 755 ${S}/JSON.lua ${D}/usr/share/lua/5.2/json.lua
}

