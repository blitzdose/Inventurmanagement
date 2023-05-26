package de.dhbw.christian.plugins.rest;

import de.dhbw.christian.adapters.section.JsonToSectionMapper;
import de.dhbw.christian.adapters.section.SectionResource;
import de.dhbw.christian.adapters.section.SectionToSectionResourceMapper;
import de.dhbw.christian.application.SectionApplicationService;
import de.dhbw.christian.domain.section.Section;
import de.dhbw.christian.javalin.extension.EndpointMapping;
import de.dhbw.christian.javalin.extension.JavalinController;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;

import java.util.List;

@JavalinController(endpoint = "/api/section")
public class SectionController {

    private final SectionApplicationService sectionApplicationService;

    public SectionController(SectionApplicationService sectionApplicationService) {
        this.sectionApplicationService = sectionApplicationService;
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "")
    public List<SectionResource> getSectionList(Context context) {
        return sectionApplicationService.findAll().stream().map(new SectionToSectionResourceMapper()).toList();
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/{sectionName}")
    public SectionResource getSectionByName(Context context) {
        return SectionToSectionResourceMapper.map(sectionApplicationService.findByName(context.pathParam("sectionName")));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/{sectionName}")
    public SectionResource createSection(Context context) {
        Section section = JsonToSectionMapper.map(context.body());
        section.setName(context.pathParam("sectionName"));
        if (sectionApplicationService.findByName(section.getName()) != null) {
            throw new RuntimeException();
        }
        section = sectionApplicationService.save(section);
        return SectionToSectionResourceMapper.map(section);
    }

    @EndpointMapping(handlerType = HandlerType.PUT, endpoint = "/{sectionName}")
    public SectionResource updateSection(Context context) {
        Section section = JsonToSectionMapper.map(context.body());
        section.setName(context.pathParam("sectionName"));
        section.setInventoryItems(sectionApplicationService.findByName(section.getName()).getInventoryItems());
        section = sectionApplicationService.save(section);
        return SectionToSectionResourceMapper.map(section);
    }

    @EndpointMapping(handlerType = HandlerType.DELETE, endpoint = "/{sectionName}")
    public boolean deleteSection(Context context) {
        sectionApplicationService.deleteByName(context.pathParam("sectionName"));
        return true;
    }
}
