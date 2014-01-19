-- Plugin for converting channels lists from neutrino-mp receivers
-- License GPL v2
-- Copyright (C) 2013,2014 Stefan Seyfried <seife@tuxboxcvs.slipkontur.de>
-- based on coolstream plugin
-- -- coolstream plugin Author focus.cst@gmail.com
-- -- Copyright (C) 2013 CoolStream International Ltd

-- flag to test as plain script, without xupnpd - cfg not defined in this case
local cst_test =  false

if not cfg then
cfg={}
cfg.tmp_path='/tmp/'
cfg.feeds_path='/tmp/'
cfg.debug=1
cfg.neutrinomp_bouquets_count = 0
cst_test = true
-- primitive http get function
function plugin_download(url)
	local p = require "posix"
	local i, j, host, path = string.find(url, "http://([^/]+)(/.+)")
	-- print("urlget host:"..host.." path:"..path)
	local res, err = p.getaddrinfo(host, "http", { family = p.AF_INET, socktype = p.SOCK_STREAM })
	if not res then
		cst_debug(0, "urlget getaddrinfo: "..err)
		return "" -- or better nil?
	end
	local fd = p.socket(p.AF_INET, p.SOCK_STREAM, 0)
	local ok, err, e = p.connect(fd, res[1])
	if err then
		cst_debug(0, "urlget connect: "..err)
		return ""
	end
	p.send(fd, "GET "..path.." HTTP/1.0\r\nHost: "..host.."\r\n\r\n")
	local data = {}
	while true do
		local b = p.recv(fd, 1024)
		if not b or #b == 0 then
			break
		end
		table.insert(data, b)
	end
	p.close(fd)
	data = table.concat(data)
	local headers, body
	i, j, headers, body = string.find(data, "^(.-\r\n)\r\n(.*)")
	return body
end

end

function cst_debug(level, msg)
	if cfg.debug>level then
		print(msg)
	end
end

function get_bouquets(response)
	local btable={}
	local count = 0
	for string in response:gmatch("[^\r\n]+") do
		cst_debug(1, "########## bouquet="..string)
		local num = string.match(string, "%d+");
		if num then
			local len = string.len(num);
			local name = string.sub(string, len+1);
			btable[num] = name
			cst_debug(1, "num="..num.." name="..btable[num]);
			count = count + 1
		end
		if cfg.neutrinomp_bouquets_count and cfg.neutrinomp_bouquets_count > 0 then
			if cfg.neutrinomp_bouquets_count <= count then
				break
			end
		end
	end
	return btable
end

function get_channels(file)
	local ctable={}
	for string in file:gmatch("[^\r\n]+") do
		idx = 1;
		if string then
			cst_debug(1, "########## channel="..string)
			local num = string.match(string, "%d+");
			if num then
				local len = string.len(num);
				local rest = string.sub(string, len+1);
				local id = string.match(rest, "%x+ ");
				len = string.len(id);
				local name = string.sub(rest, len+2);
				cst_debug(1, "num="..num.." id="..id.." name="..name)
				if id and name then
					table.insert(ctable, {id, name});
					idx = idx + 1;
				end
			end
		end
	end
	return ctable
end

-- all bouquets
-- local burl = "getbouquets"
-- only favorites
local burl = "getbouquets?fav=true"

-- without epg
-- local curl = "getbouquet?bouquet="
-- with epg
local curl = "getbouquet?epg=true&bouquet="

function neutrinomp_updatefeed(feed,friendly_name)
	local rc=false
	local feedspath = cfg.feeds_path
	if not friendly_name then
		friendly_name = feed
	end
	local ctrl_url = 'http://'..feed..'/control/'

	cst_debug(0, "plugin_download("..ctrl_url..burl..")")
	local bouquetsfile = plugin_download(ctrl_url..burl)
	local bouquets = get_bouquets(bouquetsfile)

	if not bouquets then
		return rc
	end
	local bindex
	local bouquett = {}
	for bindex,bouquett in pairs(bouquets) do
		local cindex
		local channelt = {}
		cst_debug(0,"plugin_download("..ctrl_url..curl..bindex..")")
		local xmlbouquetfile = plugin_download(ctrl_url..curl..bindex)
		local bouquet = get_channels(xmlbouquetfile)
		if bouquet then
			local m3ufilename = cfg.tmp_path.."nmp_"..friendly_name.."_bouquet_"..bindex..".m3u"
			cst_debug(0, m3ufilename)
			local m3ufile = io.open(m3ufilename,"w")
			m3ufile:write("#EXTM3U name=\""..bouquett.." ("..friendly_name..")\" plugin=neutrinomp type=ts\n")
			for cindex,channelt in pairs(bouquet) do
				local id = channelt[1];
				local name = channelt[2];
				m3ufile:write("#EXTINF:0,"..name.."\n")
				-- m3ufile:write(cst_url.."zapto?"..id.."\n")
				m3ufile:write("http://"..feed..":31339/id="..id.."\n")
			end
			m3ufile:close()
			os.execute(string.format('mv %s %s',m3ufilename,feedspath))
			rc=true
		end
	end
	return rc
end

function sendurl(url,range)
	local i, j, baseurl = string.find(url,"(.+):.+")
	cst_debug(0, "sendurl: url="..url.." baseurl="..baseurl)

	i,j,id = string.find(url, ".*id=(.+)")
	-- zap to channel
	local zap = baseurl.."/control/zapto?"..id;
	plugin_download(zap)

	if not cst_test then
		plugin_sendurl(url, url, range)
	end
end

if cst_test then
neutrinomp_updatefeed("td3", "td3")
-- cst_updatefeed("172.16.1.10","tank")
-- cst_sendurl("http://172.16.1.20:31339/id=c1f000010070277a", 0)
end

if not cst_test then
plugins['neutrinomp'] = {}
plugins.neutrinomp.name = "Neutrino MP"
plugins.neutrinomp.desc = "IP address (example: <i>192.168.0.1</i>)"
plugins.neutrinomp.updatefeed = neutrinomp_updatefeed
plugins.neutrinomp.sendurl = sendurl
plugins.neutrinomp.ui_config_vars =
{
    { "input",  "neutrinomp_bouquets_count", "int" }
}

end
