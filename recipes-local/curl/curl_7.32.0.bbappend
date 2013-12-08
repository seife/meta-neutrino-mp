# for size reasons, we don't want gnutls support

DEPENDS_coolstream = "zlib"
CURLGNUTLS_coolstream = "--without-gnutls --without-ssl"

# and on triple, we also don't want it...
CURLGNUTLS_tripledragon = "--without-gnutls --without-ssl"

# the ac_cv_sizeof_off_t=8 warrants this
PRINC := "${@int(PRINC) + 1}"

do_configure_prepend() {
	# curl checks this with _FILE_OFFSET_BITS=64...
	export ac_cv_sizeof_off_t=8
	sed -i s:OPT_GNUTLS/bin:OPT_GNUTLS:g ${S}/configure.ac
}
