package com.ai.bot.web.nlpmatch;

public class PartOfSpeechConstants {

	public static final String VV = "VV"; // 其他动词
	public static final String NN = "NN"; // 普通名词

	public static final String Ag = "Ag"; // 形语素：形容词性语素。形容词代码为 a，语素代码ｇ前面置以A。
	public static final String a = "a"; // 形容词：取英语形容词 adjective的第1个字母。
	public static final String ad = "ad"; // 副形词：直接作状语的形容词。形容词代码 a和副词代码d并在一起。
	public static final String an = "an"; // 名形词：具有名词功能的形容词。形容词代码 a和名词代码n并在一起。
	public static final String b = "b"; // 区别词：取汉字“别”的声母。
	public static final String c = "c"; // 连词：取英语连词 conjunction的第1个字母。
	public static final String dg = "dg"; // 副语素：副词性语素。副词代码为 d，语素代码ｇ前面置以D。
	public static final String d = "d"; // 副词：取 adverb的第2个字母，因其第1个字母已用于形容词。
	public static final String e = "e"; // 叹词：取英语叹词 exclamation的第1个字母。
	public static final String f = "f"; // 方位词：取汉字“方”
	public static final String g = "g"; // 语素：绝大多数语素都能作为合成词的“词根”，取汉字“根”的声母。
	public static final String h = "h"; // 前接成分：取英语 head的第1个字母。
	public static final String i = "i"; // 成语：取英语成语 idiom的第1个字母。
	public static final String j = "j"; // 简称略语：取汉字“简”的声母。
	public static final String k = "k"; // 后接成分
	public static final String l = "l"; // 习用语：习用语尚未成为成语，有点“临时性”，取“临”的声母。
	public static final String m = "m"; // 数词：取英语 numeral的第3个字母，n，u已有他用。
	public static final String Ng = "Ng"; // 名语素：名词性语素。名词代码为 n，语素代码ｇ前面置以N。
	public static final String n = "n"; // 名词：取英语名词 noun的第1个字母。
	public static final String nr = "nr"; // 人名：名词代码 n和“人(ren)”的声母并在一起。
	public static final String ns = "ns"; // 地名：名词代码 n和处所词代码s并在一起。
	public static final String nt = "nt"; // 机构团体：“团”的声母为 t，名词代码n和t并在一起
	public static final String nz = "nz"; // 其他专名：“专”的声母的第 1个字母为z，名词代码n和z并在一起。
	public static final String o = "o"; // 拟声词：取英语拟声词 onomatopoeia的第1个字母。
	public static final String p = "p"; // 介词：取英语介词 prepositional的第1个字母。
	public static final String q = "q"; // 量词：取英语 quantity的第1个字母。
	public static final String r = "r"; // 代词：取英语代词 pronoun的第2个字母,因p已用于介词。
	public static final String s = "s"; // 处所词：取英语 space的第1个字母。
	public static final String tg = "tg"; // 时语素：时间词性语素。时间词代码为 t,在语素的代码g前面置以T。
	public static final String t = "t"; // 时间词：取英语 time的第1个字母。
	public static final String u = "u"; // 助词：取英语助词 auxiliary
	public static final String vg = "vg"; // 动语素：动词性语素。动词代码为 v。在语素的代码g前面置以V。
	public static final String v = "v"; // 动词：取英语动词 verb的第一个字母。
	public static final String vd = "vd"; // 副动词：直接作状语的动词。动词和副词的代码并在一起。
	public static final String vn = "vn"; // 名动词：指具有名词功能的动词。动词和名词的代码并在一起。
	public static final String w = "w"; // 标点符号
	public static final String x = "x"; // 非语素字：非语素字只是一个符号，字母 x通常用于代表未知数、符号。
	public static final String y = "y"; // 语气词：取汉字“语”的声母
	public static final String z = "z"; // 状态词：取汉字“状”的声母的前一个字母。
	public static final String un = "un"; // 未知词：不可识别词及用户自定义词组。取英文Unkonwn首两个字母
}
