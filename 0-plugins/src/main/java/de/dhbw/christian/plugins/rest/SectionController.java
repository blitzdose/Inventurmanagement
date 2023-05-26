package de.dhbw.christian.plugins.rest;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.adapters.section.SectionResource;
import de.dhbw.christian.adapters.section.SectionToSectionResourceMapper;
import de.dhbw.christian.adapters.sectionproduct.AmountResource;
import de.dhbw.christian.adapters.sectionproduct.JsonToAmountResourceMapper;
import de.dhbw.christian.adapters.section.JsonToSectionMapper;
import de.dhbw.christian.adapters.sectionproduct.InputSectionProductResource;
import de.dhbw.christian.adapters.sectionproduct.JsonToSectionProductResourceMapper;
import de.dhbw.christian.adapters.sectionproduct.OutputSectionProductResource;
import de.dhbw.christian.adapters.sectionproduct.SectionProductToSectionProductResourceMapper;
import de.dhbw.christian.application.ProductApplicationService;
import de.dhbw.christian.application.SectionApplicationService;
import de.dhbw.christian.domain.section.Section;
import de.dhbw.christian.domain.sectionproduct.SectionProduct;
import de.dhbw.christian.javalin.extension.EndpointMapping;
import de.dhbw.christian.javalin.extension.JavalinController;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;

import java.util.List;

@JavalinController(endpoint = "/api/section")
public class SectionController {

    private final SectionApplicationService sectionApplicationService;
    private final ProductApplicationService productApplicationService;

    public SectionController(SectionApplicationService sectionApplicationService, ProductApplicationService productApplicationService) {
        this.sectionApplicationService = sectionApplicationService;
        this.productApplicationService = productApplicationService;
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
        return SectionToSectionResourceMapper.map(sectionApplicationService.save(section));
    }

    @EndpointMapping(handlerType = HandlerType.PUT, endpoint = "/{sectionName}")
    public SectionResource updateSection(Context context) {
        Section section = JsonToSectionMapper.map(context.body());
        section.setName(context.pathParam("sectionName"));
        section.setSectionProducts(sectionApplicationService.findByName(section.getName()).getSectionProducts());
        return SectionToSectionResourceMapper.map(sectionApplicationService.save(section));
    }

    @EndpointMapping(handlerType = HandlerType.DELETE, endpoint = "/{sectionName}")
    public boolean deleteSection(Context context) {
        sectionApplicationService.deleteByName(context.pathParam("sectionName"));
        return true;
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/{sectionName}/{EAN}")
    public OutputSectionProductResource getProductInSection(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));
        return SectionProductToSectionProductResourceMapper.map(sectionApplicationService.findByNameAndEAN(context.pathParam("sectionName"), ean));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/{sectionName}/{EAN}")
    public OutputSectionProductResource addProductToSection(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));

        InputSectionProductResource inputSectionProductResource = JsonToSectionProductResourceMapper.map(context.body());

        Section section = sectionApplicationService.findByName(context.pathParam("sectionName"));

        SectionProduct sectionProduct = new SectionProduct.CustomBuilder()
                .tray(inputSectionProductResource.getTray())
                .amount(inputSectionProductResource.getAmount())
                .product(productApplicationService.findByEAN(ean))
                .section(section)
                .build();

        sectionApplicationService.addProduct(section, sectionProduct);
        return SectionProductToSectionProductResourceMapper.map(sectionProduct);
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "{sectionName}/{EAN}/move/section/{newSectionName}")
    public boolean changeSection(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));

        sectionApplicationService.changeSection(
                sectionApplicationService.findByName(context.pathParam("sectionName")),
                sectionApplicationService.findByName(context.pathParam("newSectionName")),
                ean);

        return true;
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "{sectionName}/{EAN}/move/tray/{tray}")
    public OutputSectionProductResource changeTray(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));

        return SectionProductToSectionProductResourceMapper.map(sectionApplicationService.changeTray(
                sectionApplicationService.findByName(context.pathParam("sectionName")),
                ean,
                context.pathParam("tray")));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "{sectionName}/{EAN}/import")
    public OutputSectionProductResource importAmountProduct(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));
        AmountResource amountResource = JsonToAmountResourceMapper.map(context.body());

        return SectionProductToSectionProductResourceMapper.map(sectionApplicationService.importAmount(
                sectionApplicationService.findByName(context.pathParam("sectionName")),
                ean,
                amountResource.getAmount()
        ));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "{sectionName}/{EAN}/sell")
    public OutputSectionProductResource sellAmountProduct(Context context) {
        EAN ean = new EAN(context.pathParam("EAN"));
        AmountResource amountResource = JsonToAmountResourceMapper.map(context.body());

        return SectionProductToSectionProductResourceMapper.map(sectionApplicationService.sellAmount(
                sectionApplicationService.findByName(context.pathParam("sectionName")),
                ean,
                amountResource.getAmount()
        ));
    }
}
