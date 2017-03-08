local str = table.concat({...})
for h in str:gmatch("0x(....)") do
	io.write(string.char(tonumber(h:sub(1,2),16), tonumber(h:sub(3),16)))
end