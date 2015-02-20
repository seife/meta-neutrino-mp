SUMMARY = "A complete, cross-platform solution to record, convert and stream audio and video."
HOMEPAGE = "http://ffmpeg.org/"
SECTION = "libs"
LICENSE = "GPLv2+"
LICENSE_FLAGS = "commercial"

ARM_INSTRUCTION_SET = "arm"

DEPENDS = " \
	zlib libogg yasm-native \
"

DEPENDS_append_spark = "libxml2 libbluray"
DEPENDS_append_coolstream = "libxml2"

INC_PR = "r1"

inherit autotools pkgconfig

B = "${S}/build.${HOST_SYS}.${TARGET_SYS}"

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

EXTRA_FFCONF_armv7a = "--cpu=cortex-a8"
EXTRA_FFCONF ?= ""

# don't ask.
# libavcodec built with *any* optimisation (-O,-O2,-Os) dies with "floating point exception"
# in aacdec. So remove the optimization flags and add the ones I found in the manpage manually
# TODO: verify with newer compiler versions again.
TARGET_CFLAGS_remove_spark = "-O2"
TARGET_CFLAGS_append_spark = " \
	-fauto-inc-dec -fcompare-elim -fcprop-registers -fdce -fdefer-pop \
	-fdelayed-branch -fdse -fguess-branch-probability -fif-conversion2 -fif-conversion \
	-fipa-pure-const -fipa-profile -fipa-reference -fmerge-constants -fsplit-wide-types \
	-ftree-bit-ccp -ftree-builtin-call-dce -ftree-ccp -ftree-ch -ftree-copyrename \
	-ftree-dce -ftree-dominator-opts -ftree-dse -ftree-forwprop -ftree-fre \
	-ftree-phiprop -ftree-slsr -ftree-sra -ftree-pta -ftree-ter \
	-fforward-propagate  -fira-region=mixed \
	-funit-at-a-time -fomit-frame-pointer \
"

#PACKAGECONFIG ??= "bzip2 x264 ${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
#PACKAGECONFIG ??= "bzip2"
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
    \
    --cross-prefix=${TARGET_PREFIX} \
    --cc="${CC}" \
    --prefix=${prefix} \
    \
    --enable-ffmpeg \
    --enable-ffserver \
    --enable-ffplay \
    --disable-libtheora \
    --disable-libvorbis \
    --arch=${TARGET_ARCH} \
    --target-os="linux" \
    --enable-cross-compile \
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}" \
    --extra-ldflags="${TARGET_LDFLAGS}" \
    --sysroot="${STAGING_DIR_TARGET}" \
    --enable-hardcoded-tables \
    --disable-decoder=dpx \
    --disable-encoder=dpx \
    --disable-parser=dpx \
    --disable-doc \
    ${EXTRA_FFCONF} \
"
EXTRA_OECONF_append_raspberrypi = " \
	--arch=arm \
	--cpu=armv6 \
"
EXTRA_OECONF_append_coolstream = " \
	--disable-runtime-cpudetect \
	--arch=arm \
	--cpu=armv6 \
	--disable-neon \
	--disable-vfp \
	--disable-libtheora \
	--disable-libvorbis \
	--disable-decoders \
	--enable-decoder=dca \
	--enable-decoder=dvdsub \
	--enable-decoder=dvbsub \
	--enable-decoder=text \
	--enable-decoder=srt \
	--enable-decoder=subrip \
	--enable-decoder=subviewer \
	--enable-decoder=subviewer1 \
	--enable-decoder=xsub \
	--enable-decoder=pgssub \
	--enable-decoder=mp3 \
	--enable-decoder=flac \
	--enable-decoder=vorbis \
	--enable-decoder=aac \
	--enable-decoder=mjpeg \
	--enable-decoder=pcm_s16le \
	--enable-decoder=pcm_s16le_planar \
	--disable-parsers \
	--enable-parser=aac \
	--enable-parser=aac_latm \
	--enable-parser=ac3 \
	--enable-parser=dca \
	--enable-parser=mpeg4video \
	--enable-parser=mpegvideo \
	--enable-parser=mpegaudio \
	--enable-parser=h264 \
	--enable-parser=vc1 \
	--enable-parser=dvdsub \
	--enable-parser=dvbsub \
	--enable-parser=flac \
	--enable-parser=vorbis \
	--disable-demuxers \
	--enable-demuxer=aac \
	--enable-demuxer=ac3 \
	--enable-demuxer=avi \
	--enable-demuxer=mov \
	--enable-demuxer=vc1 \
	--enable-demuxer=mpegts \
	--enable-demuxer=mpegtsraw \
	--enable-demuxer=mpegps \
	--enable-demuxer=mpegvideo \
	--enable-demuxer=wav \
	--enable-demuxer=pcm_s16be \
	--enable-demuxer=mp3 \
	--enable-demuxer=pcm_s16le \
	--enable-demuxer=matroska \
	--enable-demuxer=flv \
	--enable-demuxer=rm \
	--enable-demuxer=rtsp \
	--enable-demuxer=hls \
	--enable-demuxer=hds \
	--enable-demuxer=dts \
	--enable-demuxer=wav \
	--enable-demuxer=ogg \
	--enable-demuxer=flac \
	--enable-demuxer=srt \
	--disable-encoders \
	--disable-muxers \
	--disable-filters \
	--disable-protocols \
	--enable-protocol=file \
	--enable-protocol=http \
	--enable-protocol=rtmp \
	--enable-protocol=rtmpe \
	--enable-protocol=rtmps \
	--enable-protocol=rtmpte \
	--enable-protocol=mmsh \
	--enable-protocol=mmst \
	--enable-bsfs \
	--disable-devices \
	--enable-swresample \
	--disable-postproc \
	--disable-swscale \
"

EXTRA_OECONF_append_spark = " \
	--disable-runtime-cpudetect \
	--enable-demuxers \
	--disable-devices \
	--enable-protocol=bluray \
	--enable-libbluray \
	--disable-bsfs \
	--disable-optimizations \
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
	--enable-dct \
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
	if test -d ${D}${includedir}/libavfilter/; then
		install -m 0644 ${S}/libavfilter/*.h ${D}${includedir}/libavfilter/
	fi
}

FFMPEG_LIBS = "libavcodec libavdevice libavformat \
               libavutil libpostproc libswscale libavfilter"

PACKAGES += "${PN}-vhook-dbg ${PN}-vhook ffmpeg-x264-presets"

## RSUGGESTS_${PN} = "mplayer"
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

    # Debug packages (-dbg)
    av_libdebug = av_libdir + '/.debug'
    do_split_packages(d, av_libdebug, '^lib(.*)\.so',
                      output_pattern='lib%s-dbg',
                      description='libav %s debug package',
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
