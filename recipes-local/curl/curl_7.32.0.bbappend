# for size reasons, we don't want gnutls support

DEPENDS_coolstream = "zlib"
CURLGNUTLS_coolstream = "--without-gnutls --without-ssl"

# and on triple, we also don't want it...
CURLGNUTLS_tripledragon = "--without-gnutls --without-ssl"
