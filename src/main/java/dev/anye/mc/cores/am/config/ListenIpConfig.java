package dev.anye.mc.cores.am.config;

import java.util.List;

import com.google.gson.reflect.TypeToken;

import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class ListenIpConfig extends _JsonConfig<ListenIpConfig.Data>{
	public static final String FILE = _File.getFilePath(Cores.CONFIG_DIR, "listenIp.json");
	public ListenIpConfig() {
		super(FILE, """
                {
                    "type":1,
                    "address":[
                        "127.*.*.*"
                    ]
                }
                """, new TypeToken<>(){});
	}

	@Override
	public Data getDatas() {
		if (datas == null) {
			this.datas = new Data(1, List.of("127.*.*.*"));
		}
		return super.getDatas();
	}

	public boolean checkIp(String tip){
		return getDatas().checkIp(tip);
	}

	

	public record Data(int type,List<String> address) {
		public boolean checkIp(String tip){
			return switch (type) {
				case 0 -> true;
				case 1 -> testIp(tip);
				case 2 -> !testIp(tip);
				default -> false;
			};
		}
		public boolean testIp(String tip){
			if (address.contains(tip)) return true;
			for (String ip : address){
				if (ip.contains("*")) {
					if (testIp(ip,tip,".")) return true;
					return testIp(ip,tip,":");
				}
			}
			return false;
		}

		public boolean testIp(String ip,String tip,String c){
			if (ip.contains(c) && tip.contains(c)) {
				String[] as = ip.split(c);
				String[] ips = tip.split(c);
				if (as.length != ips.length) return false;
				for(int i = 0;i < as.length ; i++){
					String a = as[i];
					if (!a.equals("*") && !a.equals(ips[i])) return false;
				}
				return true;
			}
			return false;
		}
	}
}
