# 腾讯迷の哈希
		#noinspection SpellCheckingInspection
		def hash_get(uin, ptwebqq)
			t = ''
			n = ptwebqq + 'password error'
			t << uin.to_s until t.length >= n.length
			Array.new(n.length) { |i|
				'%02X' % (t[i].ord ^ n[i].ord)
			}.join
		end