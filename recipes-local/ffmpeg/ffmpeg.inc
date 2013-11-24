SUMMARY = "A complete, cross-platform solution to record, convert and stream audio and video."
HOMEPAGE = "http://ffmpeg.org/"
SECTION = "libs"
LICENSE = "GPLv2+"
LICENSE_FLAGS = "commercial"

ARM_INSTRUCTION_SET = "arm"

DEPENDS = "zlib libogg libvorbis libtheora yasm-native"

INC_PR = "r8"

inherit autotools pkgconfig

B = "${S}/build.${HOST_SYS}.${TARGET_SYS}"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

EXTRA_FFCONF_armv7a = "--cpu=cortex-a8"
EXTRA_FFCONF ?= ""

#PACKAGECONFIG ??= "bzip2 x264 ${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
PACKAGECONFIG ??= ""
PACKAGECONFIG[jack] = "--enable-indev=jack,--disable-indev=jack,jack"
PACKAGECONFIG[bzip2] = "--enable-bzlib,--disable-bzlib,bzip2"
PACKAGECONFIG[schroedinger] = "--enable-libschroedinger,--disable-libschroedinger,schroedinger"
PACKAGECONFIG[gsm] = "--enable-libgsm,--disable-libgsm,libgsm"
PACKAGECONFIG[x264] = "--enable-libx264,--disable-libx264,x264"
PACKAGECONFIG[vpx] = "--enable-libvpx,--disable-libvpx,libvpx"
PACKAGECONFIG[mp3lame] = "--enable-libmp3lame,--disable-libmp3lame,lame"
PACKAGECONFIG[faac] = "--enable-libfaac,--disable-libfaac,faac"
PACKAGECONFIG[x11] = "--enable-x11grab,--disable-x11grab,virtual/libx11 libxfixes libxext xproto"

EXTRA_OECONF = " \
    --disable-stripping \
    --enable-pic \
    --enable-shared \
    --enable-pthreads \
    --enable-gpl \
    --enable-avfilter \
    \
    --cross-prefix=${TARGET_PREFIX} \
    --prefix=${prefix} \
    \
    --enable-ffserver \
    --enable-ffplay \
    --enable-libtheora  \
    --enable-libvorbis \
    --arch=${TARGET_ARCH} \
    --target-os="linux" \
    --enable-cross-compile \
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}" \
    --extra-ldflags="${TARGET_LDFLAGS}" \
    --sysroot="${STAGING_DIR_TARGET}" \
    --enable-hardcoded-tables \
    ${EXTRA_FFCONF} \
"
EXTRA_OECONF_append_raspberrypi = " \
	--arch=arm \
	--cpu=armv6 \
"
EXTRA_OECONF_append_coolstream = " \
	--arch=arm \
	--cpu=armv6 \
	--disable-libtheora \
	--disable-libvorbis \
"
# strip down ffmpeg for tripledragon: most of it is not useful here.
# --arch=ppc4xx seems to still have a problem with the assembler...
EXTRA_OECONF_append_tripledragon = " \
	--arch=ppc4xx \
	--disable-altivec \
	--disable-libtheora \
	--disable-libvorbis \
	--disable-encoders --disable-muxers \
	--disable-decoders --disable-protocols \
	--disable-parsers --disable-demuxers \
	--enable-protocol=file \
	--enable-encoder=mpeg2video --enable-muxer=mpeg2video \
	--enable-decoder=dvbsub --enable-demuxer=mpegps \
	--enable-parser=mjpeg --enable-demuxer=mjpeg --enable-decoder=mjpeg \
	--disable-bsfs --disable-network \
"

do_configure() {
    # We don't have TARGET_PREFIX-pkgconfig
    sed -i '/pkg_config_default="${cross_prefix}${pkg_config_default}"/d' ${S}/configure
    mkdir -p ${B}
    cd ${B}
    ${S}/configure ${EXTRA_OECONF}
    sed -i -e s:Os:O4:g ${B}/config.h
}

do_install_append() {
    install -m 0644 ${S}/libavfilter/*.h ${D}${includedir}/libavfilter/
}

FFMPEG_LIBS = "libavcodec libavdevice libavformat \
               libavutil libpostproc libswscale libavfilter"

PACKAGES += "${PN}-vhook-dbg ${PN}-vhook ffmpeg-x264-presets"

RSUGGESTS_${PN} = "mplayer"
FILES_${PN} = "${bindir} ${datadir}/ffprobe.xsd"
FILES_${PN}-dev = "${includedir}/${PN}"

FILES_${PN}-vhook = "${libdir}/vhook"
FILES_${PN}-vhook-dbg += "${libdir}/vhook/.debug"

FILES_ffmpeg-x264-presets = "${datadir}/*.ffpreset"

LEAD_SONAME = "libavcodec.so"

FILES_${PN}-dev = "${includedir}"
FILES_${PN}-dev += "${datadir}/examples"

python populate_packages_prepend() {
    av_libdir = d.expand('${libdir}')
    av_pkgconfig = d.expand('${libdir}/pkgconfig')

    # Runtime package
    do_split_packages(d, av_libdir, '^lib(.*)\.so\..*',
                      output_pattern='lib%s',
                      description='libav %s library',
                      extra_depends='',
                      prepend=True,
                      allow_links=True)

    # Development packages (-dev, -staticdev)
    do_split_packages(d, av_libdir, '^lib(.*)\.so$',
                      output_pattern='lib%s-dev',
                      description='libav %s development package',
                      extra_depends='${PN}-dev',
                      prepend=True,
                      allow_links=True)
    do_split_packages(d, av_pkgconfig, '^lib(.*)\.pc$',
                      output_pattern='lib%s-dev',
                      description='libav %s development package',
                      extra_depends='${PN}-dev',
                      prepend=True)
    do_split_packages(d, av_libdir, '^lib(.*)\.a$',
                      output_pattern='lib%s-staticdev',
                      description='libav %s development package - static library',
                      extra_depends='${PN}-dev',
                      prepend=True,
                      allow_links=True)

    if d.getVar('TARGET_ARCH', True) == 'i586':
        # libav can't be build with -fPIC for 32-bit x86
        pkgs = d.getVar('PACKAGES', True).split()
        for pkg in pkgs:
            d.appendVar('INSANE_SKIP_%s' % pkg, ' textrel')
}

PACKAGES_DYNAMIC += "^lib(av(codec|device|filter|format|util)|postproc).*"
