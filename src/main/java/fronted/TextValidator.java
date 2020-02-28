package fronted;

class TextValidator {
	
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
	boolean checkText(String str,  int size){
		if(str.length() > size){
			return false;
		}
		char[] charText = str.toCharArray();
        for (int i = 0; i < charText.length; i++) {
        	if(!checkSymbol(charText[i])) return false;
            if (isNumeric(String.valueOf(charText[i]))) return false;
        }
		return true;
	}
	boolean checkValidity(String str,  int size){
		if(str.length() > size){
			return false;
		}
		char[] charText = str.toCharArray();
        for (int i = 0; i < charText.length; i++) {
        	if(!checkSymbol(charText[i])) return false;
        }
		return true;
	}
	boolean checkPhoneNumber(String str){
		if(!isNumeric(str)) return false;
		if(str.length() != 10)return false;
		return true;
	}
	boolean checkDate(String date){
		char[] charDate = date.toCharArray();
        String str = "";
        String str1 = "";
        if (charDate.length > 10) {
            return false;
        }
        for (int i = 0; i < charDate.length; i++) {
            if (i == 2) {
                if (charDate[i] != '.') {
                    return false;
                }
            }
            if (i == 5) {
                if (charDate[i] != '.') {
                    return false;
                }
                str1 = "";
            }
            if (i < 2) {
                if (!isNumeric(String.valueOf(charDate[i]))) {
                    return false;
                }
                str += String.valueOf(charDate[i]);
                if ((Integer.parseInt(str) > 31) || (Integer.parseInt(str) < 0)) {
                    return false;
                }
            }
            if ((i > 2) & (i < 5)) {
                if (!isNumeric(String.valueOf(charDate[i]))) {
                    return false;
                }
                str1 += String.valueOf(charDate[i]);
                if ((Integer.parseInt(str1) > 12) || (Integer.parseInt(str) < 0)) {
                    return false;
                }
                if ((Integer.parseInt(str) > 28) & (Integer.parseInt(str1) == 2)) {
                    return false;
                }
                if ((Integer.parseInt(str) > 30 & ((Integer.parseInt(str1) == 4) || (Integer.parseInt(str1) == 6) || (Integer.parseInt(str1) == 9) || (Integer.parseInt(str1) == 11)))) {
                    return false;
                }
            }
            if (i > 5) {
                if (!isNumeric(String.valueOf(charDate[i]))) {
                    return false;
                }
                str1 += String.valueOf(charDate[i]);

                if (i == 9 & Integer.parseInt(str1) < 2020) {
                    return false;
                }
            }
        }
        return true;
	}
	boolean checkSymbol(char c){
        if ((c == '+') || (c == '/') || (c == '|')) {
            return false;
        }
        if ((c == '*') || (c == '(') || (c == ')') || (c == '=')) {
            return false;
        }
        if ((c == '_') || (c == '"') || (c == ';') || (c == ':')) {
            return false;
        }
        if ((c == '<') || (c == '?') || (c == '>') || (c == '.')) {
            return false;
        }
        if ((c == '{') || (c == '}') || (c == '[')) {
            return false;
        }
        if ((c == ']') || (c == '&') || (c == '!') || (c == '@')) {
            return false;
        }
        if ((c == '#') || (c == '№') || (c == '$') || (c == '%')) {
            return false;
        }
        if ((c == '^') || (c == '~') || (c == '`') || (c == '\\')) {
            return false;
        }
        if (c == '\'') {
            return false;
        }
		return true;
	}
}
