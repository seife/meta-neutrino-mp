# configure stuff goes here
#
N_CFLAGS = "-Wall -W -Wshadow -g -O2 -fno-strict-aliasing -funsigned-char -rdynamic"
N_CXXFLAGS = "${N_CFLAGS}"
N_LDFLAGS += "-Wl,-rpath-link,${STAGING_DIR_HOST}/usr/lib"

N_CPPFLAGS = "-DDYNAMIC_LUAPOSIX"
CPPFLAGS = "${N_CPPFLAGS}"
CPPFLAGS_tripledragon += "${N_CPPFLAGS} -I${STAGING_DIR_HOST}/usr/include/hardware"

EXTRA_OECONF = " \
	--enable-maintainer-mode \
	--with-target=native \
	--disable-silent-rules \
	--with-tremor \
	--disable-upnp \
	--enable-giflib \
	--enable-mdev \
	--with-stb-hal-includes=${STAGING_DIR_HOST}/usr/include/libstb-hal \
"

EXTRA_OECONF_append_raspberrypi += "\
	--with-boxtype=generic --with-boxmodel=raspi \
"

EXTRA_OECONF_append_tripledragon += "\
	--with-boxtype=tripledragon \
"

EXTRA_OECONF_append_coolstream += "\
	--with-boxtype=coolstream \
"

EXTRA_OECONF_append_spark += "\
	--with-boxtype=spark \
"

EXTRA_OECONF_append_spark7162 += "\
	--with-boxtype=spark \
"
