package dev.anye.mc.cores.am.config;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

import java.util.List;

public class ListenConfig extends _JsonConfig<ListenConfig.Data>{
	public static final String FILE = _File.getFilePath(Cores.CONFIG_DIR, "listen.json");


	public ListenConfig() {
		super(FILE,Data.DEFAULT, new TypeToken<>(){});
	}

	public boolean checkIp(String tip){
		return map(data1 -> data1.checkIp(tip)).orElse(false);
	}
	public boolean checkPath(String path){
		return map(data1 -> data1.checkPath(path)).orElse(false);
	}


	public record Data(int listenPort ,int ipType, List<String> address,int pathType,List<String> path) {
		public static final Data DEFAULT = new Data(
				4444,
				1,
				List.of("127.*.*.*"),
				1,
				List.of("assets/*"));
		public boolean checkIp(String tip){
			return switch (ipType) {
				case 0 -> true;
				case 1 -> testIp(tip);
				case 2 -> !testIp(tip);
				default -> false;
			};
		}

		public boolean checkPath(String tp){
			return switch (ipType) {
				case 0 -> true;
				case 1 -> testPath(tp);
				case 2 -> !testPath(tp);
				default -> false;
			};
		}

		public boolean testIp(String tip){
			return test(address,tip,".",":");
		}
		public boolean testPath(String tp){
			return test(path,tp,"/","\\");
		}

		public boolean test(List<String> a,String b,String... separator){
			if (a.contains(b)) return true;
			for (String s : a){
				if (s.contains("*")) {
					for (String sp : separator){
						if (test(s,b,sp)) return true;
					}
				}
			}
			return false;
		}

		public boolean test(String test,String tester,String sp){
			if (test.contains(sp) && tester.contains(sp)) {
				String[] ts = test.split(sp);
				String[] tes = tester.split(sp);
				if (ts.length > tes.length) return false;
				for(int i = 0;i < ts.length ; i++){
					String t = ts[i];
					if (t.isEmpty() || t.equals("*") || t.equals(tes[i])) continue;
					return false;
				}
				return true;
			}
			return false;
		}
	}
}
