package de.dhbw.christian.plugins.rest;

import de.dhbw.christian.EAN.abstraction.EAN;
import de.dhbw.christian.adapters.inventoryItem.*;
import de.dhbw.christian.application.InventoryItemApplicationService;
import de.dhbw.christian.application.ProductApplicationService;
import de.dhbw.christian.application.SectionApplicationService;
import de.dhbw.christian.domain.inventoryitem.InventoryItem;
import de.dhbw.christian.domain.product.Product;
import de.dhbw.christian.domain.section.Section;
import de.dhbw.christian.javalin.extension.EndpointMapping;
import de.dhbw.christian.javalin.extension.JavalinController;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;

import java.util.List;

@JavalinController(endpoint = "/api/inventory")
public class InventoryItemController {

    private final InventoryItemApplicationService inventoryItemApplicationService;

    private final SectionApplicationService sectionApplicationService;
    private final ProductApplicationService productApplicationService;

    public InventoryItemController(InventoryItemApplicationService inventoryItemApplicationService,
                                   SectionApplicationService sectionApplicationService,
                                   ProductApplicationService productApplicationService) {
        this.inventoryItemApplicationService = inventoryItemApplicationService;
        this.sectionApplicationService = sectionApplicationService;
        this.productApplicationService = productApplicationService;
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/list")
    public List<InventoryItemResource> getInventoryItemList(Context context) {
        return inventoryItemApplicationService.findAllInventoryItems().stream().map(new InventoryItemToInventoryItemResourceMapper()).toList();
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/section/{sectionName}")
    public List<InventoryItemResource> getInventoryItemListBySectionName(Context context) {
        return inventoryItemApplicationService.findAllBySectionName(context.pathParam("sectionName")).stream().map(new InventoryItemToInventoryItemResourceMapper()).toList();
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/product/{ean}")
    public List<InventoryItemResource> getInventoryItemListByProduct(Context context) {
        return inventoryItemApplicationService.findAllByEAN(new EAN(context.pathParam("ean"))).stream().map(new InventoryItemToInventoryItemResourceMapper()).toList();
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/id/{id}")
    public InventoryItemResource getInventoryItemById(Context context) {
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.findById(Long.parseLong(context.pathParam("id"))));

    }

    @EndpointMapping(handlerType = HandlerType.DELETE, endpoint = "/id/{id}")
    public boolean deleteInventoryItemById(Context context) {
        inventoryItemApplicationService.deleteById(Long.parseLong(context.pathParam("id")));
        return true;
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/id/{id}/sell")
    public InventoryItemResource sellInventoryItemById(Context context) {
        AmountResource amountResource = JsonToAmountResourceMapper.map(context.body());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.sellAmountById(Long.parseLong(context.pathParam("id")), amountResource.getAmount()));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/id/{id}/import")
    public InventoryItemResource importInventoryItemById(Context context) {
        AmountResource amountResource = JsonToAmountResourceMapper.map(context.body());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.importAmountById(Long.parseLong(context.pathParam("id")), amountResource.getAmount()));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/id/{id}/move/tray")
    public InventoryItemResource moveTrayInventoryItemById(Context context) {
        TrayResouce trayResouce = JsonToTrayResourceMapper.map(context.body());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.changeTrayById(Long.parseLong(context.pathParam("id")), trayResouce.getTray()));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/id/{id}/move/section")
    public InventoryItemResource moveSectionInventoryItemById(Context context) {
        SectionNameResource sectionResource = JsonToSectionNameResourceMapper.map(context.body());
        Section section = sectionApplicationService.findByName(sectionResource.getSectionName());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.changeSectionById(Long.parseLong(context.pathParam("id")), section));
    }

    @EndpointMapping(handlerType = HandlerType.GET, endpoint = "/")
    public InventoryItemResource getInventoryItemByEANAndSectionName(Context context) {
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.findByEANAndSectionName(new EAN(context.queryParam("EAN")), context.queryParam("Section")));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/")
    public InventoryItemResource createInventoryItem(Context context) {
        InventoryItemResource inventoryItemResource = JsonToInventoryItemResourceMapper.map(context.body());
        Section section = sectionApplicationService.findByName(inventoryItemResource.getSectionName());
        Product product = productApplicationService.findByEAN(new EAN(inventoryItemResource.getEan()));

        InventoryItem inventoryItem = new InventoryItem.CustomInventoryItemBuilder()
                .section(section)
                .product(product)
                .amount(inventoryItemResource.getAmount())
                .tray(inventoryItemResource.getTray())
                .build();

        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.save(inventoryItem));
    }

    @EndpointMapping(handlerType = HandlerType.DELETE, endpoint = "/")
    public boolean deleteInventoryItemByEANAndSectionName(Context context) {
        inventoryItemApplicationService.deleteByEANAndSectionName(new EAN(context.queryParam("EAN")), context.queryParam("Section"));
        return true;
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/sell")
    public InventoryItemResource sellInventoryItemByEANAndSectionName(Context context) {
        AmountResource amountResource = JsonToAmountResourceMapper.map(context.body());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.sellAmountByEANAndSectionName(new EAN(context.queryParam("EAN")), context.queryParam("Section"), amountResource.getAmount()));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/import")
    public InventoryItemResource importInventoryItemByEANAndSectionName(Context context) {
        AmountResource amountResource = JsonToAmountResourceMapper.map(context.body());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.importAmountByEANAndSectionName(new EAN(context.queryParam("EAN")), context.queryParam("Section"), amountResource.getAmount()));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/move/tray")
    public InventoryItemResource moveTrayInventoryItemByEANAndSectionName(Context context) {
        TrayResouce trayResouce = JsonToTrayResourceMapper.map(context.body());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.changeTrayByEANAndSectionName(new EAN(context.queryParam("EAN")), context.queryParam("Section"), trayResouce.getTray()));
    }

    @EndpointMapping(handlerType = HandlerType.POST, endpoint = "/move/section")
    public InventoryItemResource moveSectionInventoryItemByEANAndSectionName(Context context) {
        SectionNameResource sectionResource = JsonToSectionNameResourceMapper.map(context.body());
        Section section = sectionApplicationService.findByName(sectionResource.getSectionName());
        return InventoryItemToInventoryItemResourceMapper.map(inventoryItemApplicationService.changeSectionByEANAndSectionName(new EAN(context.queryParam("EAN")), context.queryParam("Section"), section));
    }
}
