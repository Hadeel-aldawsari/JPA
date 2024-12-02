package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.OrderItem;
import com.example.capstone1.Repository.MerchantRepository;
import com.example.capstone1.Repository.MerchantStockRepository;
import com.example.capstone1.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

   private final MerchantRepository merchantRepository;
   private final MerchantStockRepository merchantStockRepository;
   private final ProductRepository productRepository;
    private final MerchantService merchantService;
    private final ProductService productService;

    public List<MerchantStock> getAll() {
        return merchantStockRepository.findAll();
    }

    public Boolean add(MerchantStock merchantStock) {
        if (merchantRepository.getById(merchantStock.getMerchantid()) != null &&
                productService.getProductByID(merchantStock.getProductid()) != null) {
            merchantStockRepository.save(merchantStock);
            return true;
        }
        return false;
    }

    public Boolean update(Integer id, MerchantStock updatedMerchantStock) {
        MerchantStock old=merchantStockRepository.getById(id);
        if(old==null)return false;

        old.setStock(updatedMerchantStock.getStock());
        old.setMerchantid(updatedMerchantStock.getMerchantid());
        old.setProductid(updatedMerchantStock.getProductid());
        merchantStockRepository.save(updatedMerchantStock);
        return true;
    }

    public Boolean delete(Integer id) {
        MerchantStock merchantStock=merchantStockRepository.getById(id);
        if(merchantStock==null)return false;

        merchantStockRepository.delete(merchantStock);
        return true;
    }

    // Add stock to a specific product and merchant
    public Boolean addStock(Integer productId, Integer merchantId, int additionalStock) {
        for (MerchantStock m : merchantStockRepository.findAll()) {
            if (m.getMerchantid().equals(merchantId) && m.getProductid().equals(productId)) {
                m.setStock(m.getStock() + additionalStock);
                return true;
            }
        }
        return false;
    }

    // Check if a product is in stock for a merchant
    public Boolean isInStock(Integer productid, Integer merchantId) {
        for (MerchantStock m : merchantStockRepository.findAll()) {
            if (m.getProductid().equals(productid) && m.getMerchantid().equals(merchantId)) {
                if (m.getStock() > 0) return true;
            }
        }
        return false;
    }

    // Get stock for a specific product and merchant
    public Integer getStock(Integer merchantId, Integer productid) {
        for (MerchantStock m : merchantStockRepository.findAll()) {
            if (m.getProductid().equals(productid) && m.getMerchantid().equals(merchantId)) {
                return m.getStock();
            }
        }
        return 0;
    }

    // Reduce stock after a purchase
    public void reduceStock(Integer merchantId, OrderItem item) {
        for (MerchantStock m : merchantStockRepository.findAll()) {
            if (m.getMerchantid().equals(merchantId) && m.getProductid().equals(item.getId())) {
                m.setStock(m.getStock() - item.getQuantity());
            }
        }
    }

    // Restock a product for a merchant
    public void restock(Integer merchantId, Integer productId, int quantity) {
        for (MerchantStock m : merchantStockRepository.findAll()) {
            if (m.getMerchantid().equals(merchantId) && m.getProductid().equals(productId)) {
                m.setStock(m.getStock() + quantity);
            }
        }
    }

    // Check if merchant's stock exists
    public Boolean isMerchantsStockExist(Integer merchantsid) {
        for (MerchantStock m : merchantStockRepository.findAll()) {
            if (m.getMerchantid().equals(merchantsid)) {
                return true;
            }
        }
        return false;
    }
}
