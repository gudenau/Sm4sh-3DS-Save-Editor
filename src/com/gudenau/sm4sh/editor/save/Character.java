package com.gudenau.sm4sh.editor.save;

public class Character {
		public boolean ness = false;
		public boolean falco = false;
		public boolean wario = false;
		public boolean lucina = false;
		public boolean darkPit = false;
		public boolean drMario = false;
		public boolean rob = false;
		public boolean ganondorf = false;
		public boolean gameAndWatch = false;
		public boolean bowserJr = false;
		public boolean duckHunt = false;
		public boolean jigglypuff = false;
		
		public static Character load(long chars){
			Character ret = new Character();
			
			ret.ness			= (chars & 0x0000000000000002L) != 0;
			ret.falco			= (chars & 0x0000000000000010L) != 0;
			ret.wario			= (chars & 0x0000000000000080L) != 0;
			ret.lucina			= (chars & 0x0000000000000400L) != 0;
			ret.darkPit			= (chars & 0x0000000000002000L) != 0;
			ret.drMario			= (chars & 0x0000000000010000L) != 0;
			ret.rob				= (chars & 0x0000000000080000L) != 0;
			ret.ganondorf		= (chars & 0x0000000000400000L) != 0;
			ret.gameAndWatch	= (chars & 0x0000000002000000L) != 0;
			ret.bowserJr		= (chars & 0x0000000010000000L) != 0;
			ret.duckHunt		= (chars & 0x0000000080000000L) != 0;
			ret.jigglypuff		= (chars & 0x0000000400000000L) != 0;
			
			return ret;
		}
		
		public long save(){
			long ret = 0x0000000249249249L;
			
			ret |= ness			? 0x0000000000000002L : 0L;
			ret |= falco		? 0x0000000000000010L : 0L;
			ret |= wario		? 0x0000000000000080L : 0L;
			ret |= lucina		? 0x0000000000000400L : 0L;
			ret |= darkPit		? 0x0000000000002000L : 0L;
			ret |= drMario		? 0x0000000000080000L : 0L;
			ret |= rob			? 0x0000000000080000L : 0L;
			ret |= ganondorf	? 0x0000000000400000L : 0L;
			ret |= gameAndWatch	? 0x0000000002000000L : 0L;
			ret |= bowserJr		? 0x0000000010000000L : 0L;
			ret |= duckHunt		? 0x0000000080000000L : 0L;
			ret |= jigglypuff	? 0x0000000400000000L : 0L;
			
			return ret;
		}

		@Override
		public String toString() {
			return "Character [ness=" + ness + ", falco=" + falco + ", wario="
					+ wario + ", lucina=" + lucina + ", darkPit=" + darkPit
					+ ", drMario=" + drMario + ", rob=" + rob + ", ganondorf="
					+ ganondorf + ", gameAndWatch=" + gameAndWatch
					+ ", bowserJr=" + bowserJr + ", duckHunt=" + duckHunt
					+ ", jigglypuff=" + jigglypuff + "]";
		}
	}