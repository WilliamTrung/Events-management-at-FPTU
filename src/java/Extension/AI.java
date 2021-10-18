/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Extension;

import DAO.SlotDAO;
import DTO.SlotDTO;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author WilliamTrung
 */
public class AI {

    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
            + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
            + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static String inputVietnamese(String input) {
        try {
            byte[] bytes = input.getBytes("ISO-8859-1");
            input = new String(bytes, "UTF-8");
        } catch (Exception e) {
            e.toString();
        }
        return input;
    }

    public static List<SlotDTO> checkChosenSlot(String[] uri, List<SlotDTO> slots) {
        List<SlotDTO> list = new ArrayList<>();
        Calendar c = new Calendar();
        Date preDay = c.convertToDate(uri[0].split("-")[1]);
        Date nextDay = Date.valueOf(LocalDate.now());
        if (!preDay.before(nextDay)) {
            SlotDTO startSlot = null;
            SlotDTO endSlot = null;
            boolean check = true;
            for (String str : uri) {
                nextDay = c.convertToDate(str.split("-")[1]);
                if (!preDay.equals(nextDay)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                startSlot = new SlotDAO().getSlot(slots, uri[0].split("-")[0]);
                endSlot = new SlotDAO().getSlot(slots, uri[uri.length - 1].split("-")[0]);
                list.add(startSlot);
                list.add(endSlot);
            }
        } else {
            list = null;
        }
        return list;
    }

    public static List<SlotDTO> checkChosenUpdateSlot(String[] uri, List<SlotDTO> slots) {
        List<SlotDTO> list = new ArrayList<>();
        /*
        int index = uri.indexOf("-");
        int length = uri.length()-1;
        String temp = uri.substring(index+1, length+1);
        Date preDay = Date.valueOf(temp);
         */
        Date preDay = Date.valueOf(uri[0].substring(uri[0].indexOf("-") + 1, uri[0].length()));
        Date nextDay = Date.valueOf(LocalDate.now());
        if (!preDay.before(nextDay)) {
            SlotDTO startSlot = null;
            SlotDTO endSlot = null;
            boolean check = true;
            for (String str : uri) {
                nextDay = Date.valueOf(str.substring(str.indexOf("-") + 1, str.length()));
                if (!preDay.equals(nextDay)) {
                    check = false;
                    break;
                }
            }
            if (check) {
                startSlot = new SlotDAO().getSlot(slots, uri[0].split("-")[0]);
                endSlot = new SlotDAO().getSlot(slots, uri[uri.length - 1].split("-")[0]);
                list.add(startSlot);
                list.add(endSlot);
            }
        } else {
            list = null;
        }
        return list;
    }

    public static List<String> detectEmbededLinks(String description) {
        List<String> list = new ArrayList<>();
        Matcher matcher = urlPattern.matcher(description);
        String embed = "embed/";
        String youtube = "watch?v=";
        String youtube_share = "youtu.be";
        String youtube_share_embeded = "www.youtube.com/" + embed;
        //https://youtu.be/kFZo7yJ2ONg
        //https://www.youtube.com/watch?v=kFZo7yJ2ONg&list=RDMMAlXfbVpDUdo
        int count = 0;
        int index = 0;
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();
            // now you have the offsets of a URL match
            list.add(description.substring(index, matchStart));
            count++;
            list.add(description.substring(matchStart, matchEnd));
            if (list.get(count).contains(youtube)) {
                String t = list.get(count);
                String temp = t.replace(youtube, embed);
                temp = temp.split("&list")[0];

                list.set(count, temp);
            } else if (list.get(count).contains(youtube_share)) {
                String t = list.get(count);
                String temp = t.replace(youtube_share, youtube_share_embeded);
                temp = temp.split("&list")[0];
                list.set(count, temp);
            }

            count++;
            index = matchEnd + 1;
        }
        return list;
    }

    public static void main(String[] args) {
        String url = "https://www.youtube.com/watch?v=5Lm6NModzTM";
        List<String> embed = detectEmbededLinks(url);
        for (String s : embed) {
            System.out.println(s);
        }
    }
}
