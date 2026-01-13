package com.gabid.ezaciancraft.lib.research;

import thaumcraft.api.research.ResearchPage;

import static com.gabid.ezaciancraft.CoreMod.MODID;

public class ResearchUtils {
    public static ResearchPage createPageTranslation(String entryName) {
        return new ResearchPage(MODID+".research_page."+entryName.toUpperCase());
    }

    public static ResearchPage createPageTranslation(String entryName, int id) {
        return new ResearchPage(MODID+".research_page."+entryName.toUpperCase()+"."+id);
    }

    public static ResearchPage[] createVariousPageIndex(String entryName, int size) {
        ResearchPage[] researchPagesToRegister = new ResearchPage[size];
        int id = 1;
        for(int page=0; page < size; page++) {
            researchPagesToRegister[page] = new ResearchPage(MODID+".research_page."+entryName.toUpperCase()+"."+id);
            id++;
        }
        return researchPagesToRegister;
    }
}
